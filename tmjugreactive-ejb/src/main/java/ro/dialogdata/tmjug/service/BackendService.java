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
package ro.dialogdata.tmjug.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ro.dialogdata.tmjug.data.ContactRepository;
import ro.dialogdata.tmjug.data.OrderRepository;
import ro.dialogdata.tmjug.data.ProductRepository;
import ro.dialogdata.tmjug.model.Contact;
import ro.dialogdata.tmjug.model.Order;
import ro.dialogdata.tmjug.model.Product;

@Stateless
public class BackendService {

    @Inject
    private Logger log;

    @Inject
    private ContactRepository contactRepository;
    
    @Inject
    private ProductRepository productRepository;

    @Inject
    private OrderRepository orderRepository;
    

    public Order getOrderById(final String id) {
        log.info("Retrieving order for order Id: " + id);
        return orderRepository.findByOrderId(id);
    }
    
    public Contact[] getContactsFor(final String orderId) {
        log.info("Loading all contacts for order: " + orderId);
        List<Contact> contacts = contactRepository.findByOrderId(orderId);
        Contact[] response = new Contact[0];
        if (contacts != null) {
        	return contacts.toArray(response);
        }
        
        return response;
    }
    
    public Product[] getProductFor(final String orderId) {
    	log.info("Loading all products for order: " + orderId);
        List<Product> products = productRepository.findByOrderId(orderId);
        Product[] response = new Product[0];
        if (products != null) {
        	return products.toArray(response);
        }
        
        return response;
    }
}
