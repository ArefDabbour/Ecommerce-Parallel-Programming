package Threads;

import com.example.demo.Services.ProductService;

public class AlterThread implements Runnable {
	private Integer quantity;

	public AlterThread(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	//Synchronized to solve the RC
	public void run() {
		int current = ProductService.productToSave.getQuantity();
		int newValue = current + quantity;
		ProductService.productToSave.setQuantity(newValue);
	}
}
