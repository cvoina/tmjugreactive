package ro.dialogdata.tmjug.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ro.dialogdata.tmjug.model.Order;

@Stateless
public class OrderRegistrationService {
	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Order> orderEventSrc;

	public void register(Order order) throws Exception {
		log.info("Registering " + order.getOrderId());
		em.persist(order);
		orderEventSrc.fire(order);
	}

}
