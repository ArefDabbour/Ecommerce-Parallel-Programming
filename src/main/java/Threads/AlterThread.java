package Threads;

import com.example.demo.Services.ProductService;

public class AlterThread implements Runnable {
	private Integer quantity;

	public AlterThread(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public void  run() {
		try {			
			ProductService.read_modify_write(quantity);
		}finally {
			ProductService.latch.countDown();
		}
	}
}
