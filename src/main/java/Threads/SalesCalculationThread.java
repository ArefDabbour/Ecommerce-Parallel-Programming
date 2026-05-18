package Threads;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Entities.PurchaseOrder;
import com.example.demo.Repositories.PurchaseOrederRepository;
import com.example.demo.Services.CustomerService;

public class SalesCalculationThread implements Runnable {
	PurchaseOrederRepository repository;

	public SalesCalculationThread(PurchaseOrederRepository repo) {
		repository = repo;
	}

	@Override
	public void run() {
		int reports = 0;
		List<PurchaseOrder> orders;
		while (true) {
			synchronized (CustomerService.lock) {				
				orders =  repository.findAll();
			}
			final int ordersSize = orders.size();
			ArrayList<Float> totalOrdersValues = new ArrayList<>(ordersSize);
			for (int i = 0; i < ordersSize; i++) {
				totalOrdersValues.add(i, orders.get(i).getTotal_price());
			}
			Float totalSales = 0.0f;
			for (Float value : totalOrdersValues) {
				totalSales += value.floatValue();
			}
			reports++;
			System.out.println("Report #" + reports + " total sales : " + totalSales + " \n");
			System.out.println(totalOrdersValues.size());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
