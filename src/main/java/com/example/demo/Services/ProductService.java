package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Product;
import com.example.demo.Repositories.ProductReposirty;

import Threads.AlterThread;

@Service
public class ProductService {
	@Autowired
	ProductReposirty productReposirty;

	private final List<Thread> threads = new ArrayList<>();

	public static  Product productToSave = null;

	public ResponseEntity<String> runThreads() {
		for (Thread task : threads) {
			task.start();
		}
		productReposirty.save(productToSave);
		threads.clear();
		productToSave = null;
		return ResponseEntity.ok("All threads have ran sucessfully");
	}

	public ResponseEntity<String> alter_product_quantity(Integer quantity, Long p_id) {
		Optional<Product> product = productReposirty.findById(p_id);
		if (product.isPresent()) {
			if(productToSave == null) {
				productToSave = product.get();
			}
			threads.add(new Thread(new AlterThread(quantity)));
			return ResponseEntity.ok().body("New AlterThread was created");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}

	}

	public List<Product> list_all_products() {
		List<Product> products = productReposirty.findAll();
		return products;
	}

	public String add_product(Product product) {
		productReposirty.save(product);
		return "product was added successfully";
	}

	public Product getProduct(Long id) {
		return productReposirty.findById(id).get();
	}

	public String alter_product_name(String name, Long p_id) {
		Optional<Product> product = productReposirty.findById(p_id);
		if (product.isPresent()) {
			product.get().setName(name);
			productReposirty.save(product.get());
			return "Name was altered sucessfully";
		} else {
			return "No such product";
		}
	}

	public String alter_product_price(Float price, Long p_id) {
		Optional<Product> product = productReposirty.findById(p_id);
		if (product.isPresent()) {
			product.get().setPrice(price);
			productReposirty.save(product.get());
			return "Price was altered sucessfully";
		} else {
			return "No such product";
		}
	}
}
