package com.example.demo.Controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Product;
import com.example.demo.Services.ProductService;

@RequestMapping("/admin")
@RestController
public class AdminController {

	@Autowired
	ProductService productService;

	@GetMapping("/run-threads")
	public ResponseEntity<String> runThread() {
		return productService.runThreads();
	}
	
	@GetMapping("/start-reports")
	public void start_reports() {
		productService.start_reports();
	}

	@PostMapping("/add-product")
	public String add_product(@RequestBody Product product) {
		return productService.add_product(product);
	}

	@PostMapping("/alter-product/name/{p_id}")
	public CompletableFuture alter_product_name(@RequestBody String newName, @PathVariable Long p_id) {
		return productService.alter_product_name(newName, p_id);
	}

	@PostMapping("/alter-product/price/{p_id}/{price}")
	public ResponseEntity<String> alter_product_price( @PathVariable Long p_id, @PathVariable Float price) {
		return productService.alter_product_price(price, p_id);
	}

	@PostMapping("/alter-product/quantity/{p_id}")
	public ResponseEntity<String> alter_product_quantity(@RequestBody Integer newQuantity, @PathVariable Long p_id) {
		return productService.alter_product_quantity(newQuantity, p_id);
	}
}
