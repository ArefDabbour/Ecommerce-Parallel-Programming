package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


	@PostMapping("/add-product")
	public String add_product(@RequestBody Product product) {
		return productService.add_product(product);
	}

	@PostMapping("/alter-product/name/{p_id}")
	public String alter_product_name(@RequestBody String newName, @PathVariable Long p_id) {
		return productService.alter_product_name(newName, p_id);
	}

	@PostMapping("/alter-product/price/{p_id}")
	public String alter_product_price(@RequestBody Float newPrice, @PathVariable Long p_id) {
		return null;
	}

	@PostMapping("/alter-product/quantity/{p_id}")
	public ResponseEntity<String> alter_product_quantity(@RequestBody Integer newQuantity, @PathVariable Long p_id) {
		return productService.alter_product_quantity(newQuantity, p_id);
	}
}
