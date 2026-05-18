package Threads;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Entities.PurchaseOrder;
import com.example.demo.Repositories.PurchaseOrederRepository;
import com.example.demo.Services.CustomerService;

public class BuyThread implements Runnable{
	
	private PurchaseOrder order = null;
	
	private CustomerService customerService;
	@Autowired
	PurchaseOrederRepository repository;
	public BuyThread(Long c_id, Long p_id, int quantity, float price_per_unit, float total_price, CustomerService customerService) {
		order = new PurchaseOrder();
		order.setCustomer_id(c_id);
		order.setProduct_id(p_id);
		order.setOrdered_quantity(quantity);
		order.setPrice_per_unit(price_per_unit);
		order.setTotal_price(total_price);
		this.customerService = customerService;
		
	}
	@Override
	public void run() {
//		customerService.modifyQuantity(order.getOrdered_quantity(), order);
//	    synchronized (this) { 
//	        try {
//	            this.wait();  
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	    }
	}

}
