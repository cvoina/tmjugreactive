package ro.dialogdata.tmjug.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ro.dialogdata.tmjug.data.OrderRepository;
import ro.dialogdata.tmjug.model.Order;

@RequestScoped
public class OrderListProducer {

	@Inject
	private OrderRepository orderRepository;

	private List<Order> orders;

	@Produces
	@Named
	public List<Order> getOrders() {
		if (orders == null) {
			orders = new ArrayList<Order>();
		}
		
		return orders;
	}
	
	public void onOrderListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) 
			final Order order) {
		
		getOrders().add(order);
    }

    @PostConstruct
    public void retrieveAllOrdersOrdered() {
        orders = orderRepository.listAllOrders();
    }


}
