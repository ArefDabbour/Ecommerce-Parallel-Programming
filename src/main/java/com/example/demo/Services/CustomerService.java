package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.PurchaseOrder;
import com.example.demo.Repositories.CustomerRepository;
import com.example.demo.Repositories.ProductReposirty;
import com.example.demo.Repositories.PurchaseOrederRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductService productService;

	@Autowired
	PurchaseOrederRepository purchaseOrederRepository;

	@Autowired
	ProductReposirty productReposirty;

	public Customer getCustomer(Long c_id) {
		return customerRepository.findById(c_id).get();
	}

	int counter = 0;

	private Product sharedProduct = null;
	public static Object lock = new Object();

	public ResponseEntity<String> buy(Long c_id, Long p_id, int quantity) {
		counter++;
		Product product = productService.getProduct(p_id);
		if (sharedProduct == null) {
			sharedProduct = product;
		}
		if (product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found \n");
		}
		Customer customer = getCustomer(c_id);
		if (customer == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found \n");
		}
		synchronized (sharedProduct) {
			if (sharedProduct.getQuantity() < quantity || sharedProduct.getQuantity() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Cannot exceed the available quantity" + sharedProduct.getQuantity() + " \n");
			}
			new Thread(() -> {
				sharedProduct.setQuantity(sharedProduct.getQuantity() - quantity);
				productReposirty.save(sharedProduct);
			}).start();
		}
		new Thread(() -> {
			synchronized (lock) {
				purchaseOrederRepository.save(
						new PurchaseOrder(c_id, p_id, quantity, product.getPrice(), quantity * product.getPrice()));
			}

		}).start();
		return ResponseEntity.ok("Done !! Thread Number : " + counter + "\n");
	}
}
