package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Product;
import com.example.demo.Services.CustomerService;
import com.example.demo.Services.ProductService;

@RequestMapping("/customer")
@RestController
public class CustomerController {
	
	@Autowired 
	ProductService productService;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/list-products")
	public List<Product> list() {
		return productService.list_all_products();
	}
	@GetMapping("/get-product/{id}")
	public Product getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}
	
	@GetMapping("/buy/{c_id}/{p_id}/{quantity}")
	public ResponseEntity<String> buy(@PathVariable Long c_id, @PathVariable Long p_id, @PathVariable int quantity){
		return customerService.buy(c_id, p_id, quantity);
	}
//	@GetMapping("/run-threads")
//	public ResponseEntity<String> run_threads(){
//		return customerService.run_threads();
//	}
}
