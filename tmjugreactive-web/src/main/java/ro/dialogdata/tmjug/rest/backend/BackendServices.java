package ro.dialogdata.tmjug.rest.backend;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import ro.dialogdata.tmjug.model.Contact;
import ro.dialogdata.tmjug.model.Order;
import ro.dialogdata.tmjug.model.Product;
import ro.dialogdata.tmjug.service.BackendService;

/**
 * JAX-RS Async Backend Services Access
 */
@Path("/backend/orders")
@RequestScoped
public class BackendServices {

	 @Inject
	 private BackendService backendService;
	
	 @GET 
	 @Path("{orderId}")
	 @Produces(APPLICATION_JSON)
	 public Order getOrderFor(
	      @PathParam("orderId") String orderId) {
		 
		 return backendService.getOrderById(orderId);
	 }

	 @GET 
	 @Path("{orderId}/contacts")
	 @Produces(APPLICATION_JSON)
	 public Contact[] getContactsFor(
	      @PathParam("orderId") String orderId) {

		 return backendService.getContactsFor(orderId);
	 }
	 
	 @GET 
	 @Path("{orderId}/products")
	 @Produces(APPLICATION_JSON)
	 public Product[] getProductsFor(
	      @PathParam("orderId") String orderId) {
		 
		 return backendService.getProductFor(orderId);
	 }
	 
	 @GET 
	 @Path("{orderId}/contacts/async")
	 @Produces(APPLICATION_JSON)
	 public void getAsyncContactsFor(
	      @PathParam("orderId") String orderId,
	      @Suspended AsyncResponse response) {

		 final Contact[] orderContacts = 
				 backendService.getContactsFor(orderId);
		 try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 response.resume(orderContacts);
	 }
	 
	 @GET 
	 @Path("{orderId}/products/async")
	 @Produces(APPLICATION_JSON)
	 public void getAsyncProductsFor(
	      @PathParam("orderId") String orderId,
	      @Suspended AsyncResponse response) {
		 
		 final Product[] orderProducts = 
				 backendService.getProductFor(orderId);
		 
		 response.resume(orderProducts);
	 }
	 
	
}
