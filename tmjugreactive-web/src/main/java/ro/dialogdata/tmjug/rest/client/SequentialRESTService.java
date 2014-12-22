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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import ro.dialogdata.tmjug.domain.OrderOverview;
import ro.dialogdata.tmjug.model.Contact;
import ro.dialogdata.tmjug.model.Order;
import ro.dialogdata.tmjug.model.Product;

/**
 * JAX-RS Service for Sequential Reactive Programming Demo
 */
@Path("/sequential")
@RequestScoped
public class SequentialRESTService {
	
	@Inject 
	private WebTarget backendServices;
    
    @GET
    @Path("/overview/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderOverview getOrderOverviewBy(@PathParam("orderId") String orderId) {
    	
    	Order order = retrieveOrderBy(orderId);
    	
        Contact[] contacts = getContactsOf(order);
        Product[] products = getProductsOf(order);
        
        return createOrderOverview(order, contacts, products);
    }

    private Order retrieveOrderBy(final String orderId) {
    	return backendServices.path(orderId).request().get(Order.class);
    }
    
    private OrderOverview createOrderOverview(Order order, Contact[] contacts, Product[] products) {
    	OrderOverview overview = new OrderOverview(order);
    	overview.setContacts(contacts);
    	overview.setProducts(products);
    	return overview;
    }
    
    private Contact[] getContactsOf(final Order order) {
    	return backendServices.path(order.getOrderId()).path("contacts").request().get(Contact[].class);
    }
    
    
    private Product[] getProductsOf(final Order order) {
    	return backendServices.path(order.getOrderId()).path("products").request().get(Product[].class);
    }

}
