/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.dialogdata.tmjug.rest.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.dialogdata.tmjug.domain.OrderOverview;
import ro.dialogdata.tmjug.model.Contact;
import ro.dialogdata.tmjug.model.Order;
import ro.dialogdata.tmjug.model.Product;

/**
 * JAX-RS Service for CompletableFuture Reactive Programming Demo
 */
@Path("/completabelfuture")
@RequestScoped
public class CompletedFurtureRESTService {
	
    @Inject
    private Logger log;
    
	@Inject 
	private WebTarget backendServices;

	@Resource
	private ManagedExecutorService executor;
	 
    @GET
    @Path("/overview/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getOrderOverviewBy(@PathParam("orderId") String orderId, 
    		@Suspended AsyncResponse response) 
    		throws InterruptedException, ExecutionException, TimeoutException {
    	
    	CompletableFuture<Order> orderFuture = CompletableFuture.supplyAsync(
    			() -> retrieveOrderBy(orderId), executor);
    	
    	CompletableFuture<Contact[]> contactsFuture = orderFuture.thenComposeAsync(
    			o -> getAsyncContactsOf(o), executor);
    	CompletableFuture<Product[]> productsFuture = orderFuture.thenComposeAsync(
    			o -> getAsyncProductsOf(o), executor);
    	
    	orderFuture.thenApplyAsync(o -> new OrderOverview(o), executor)
    	.thenCombineAsync(contactsFuture, (overview, contacts) -> overview.add(contacts), executor)
    	.thenCombineAsync(productsFuture, (overview, products) -> overview.add(products), executor)
    	.whenCompleteAsync(
    			(overview, throwable) -> {
					boolean exception = overview == null ? response.resume(throwable) : response.resume(overview);
    			}
    		);
    	
    	log.info("Execution gets here wihtou waiting for the others to finish.");
    	
    	response.setTimeout(2, TimeUnit.SECONDS);
    	response.setTimeoutHandler(r -> r.resume(new WebApplicationException(Response.Status.SERVICE_UNAVAILABLE)));
    }
    
    private CompletableFuture<Contact[]> getAsyncContactsOf(final Order order) {
    	String path = "contacts/async";
    	CompletableFuture<Contact[]> cf = new CompletableFuture<Contact[]>();
    	
    	backendServices.path(order.getOrderId()).path(path).request().async().get(new InvocationCallback<Contact[]>() {
			@Override
			public void completed(Contact[] contacts) {
				cf.complete(contacts);
			}

			@Override
			public void failed(Throwable throwable) {
				cf.completeExceptionally(throwable);
			}
			
		});
    	
    	return cf;
    }


    private CompletableFuture<Product[]> getAsyncProductsOf(final Order order) {
    	String path = "products/async";
    	CompletableFuture<Product[]> cf = new CompletableFuture<Product[]>();
    	
    	backendServices.path(order.getOrderId()).path(path).request().async().get(new InvocationCallback<Product[]>() {
			@Override
			public void completed(Product[] products) {
				cf.complete(products);
			}

			@Override
			public void failed(Throwable throwable) {
				cf.completeExceptionally(throwable);
			}
			
		});
    	
    	return cf;
    }

    private Order retrieveOrderBy(final String orderId) {
    	return backendServices.path(orderId).request().get(Order.class);
    }

}
