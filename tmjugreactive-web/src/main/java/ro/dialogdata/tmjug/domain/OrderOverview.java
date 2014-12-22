package ro.dialogdata.tmjug.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import ro.dialogdata.tmjug.model.Contact;
import ro.dialogdata.tmjug.model.Order;
import ro.dialogdata.tmjug.model.Product;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class OrderOverview implements Serializable {

	/**
	 * Serial UUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Order order;
	
	private Contact[] contacts;
	private Product[] products;
	
	public OrderOverview(final Order order) {
		this.order = order;
	}
	
	public OrderOverview(OrderOverview source, Contact[] contacts) {
		copy(source);
		this.contacts = contacts;
	}
	
	public OrderOverview(OrderOverview source, Product[] products) {
		copy(source);
		this.products = products;
	}
	
	public OrderOverview(Contact[] contacts, Product[] products) {
		this.contacts = contacts;
		this.products = products;
	}

	public Contact[] getContacts() {
		return contacts;
	}

	public void setContacts(Contact[] contacts) {
		this.contacts = contacts;
	}

	public Product[] getProducts() {
		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;
	}
	
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private void copy(OrderOverview source) {
		this.order = source.getOrder();
		this.contacts = source.getContacts();
		this.products = source.getProducts();
	}
	
	public OrderOverview add(Contact[] contacts) {
		return new OrderOverview(this, contacts);
	}
	
	public OrderOverview add(Product[] products) {
		return new OrderOverview(this, products);
	}
}
