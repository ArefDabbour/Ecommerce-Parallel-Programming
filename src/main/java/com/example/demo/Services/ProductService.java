package com.example.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Product;
import com.example.demo.Repositories.ProductReposirty;
import com.example.demo.Repositories.PurchaseOrederRepository;

import Threads.AlterThread;
import Threads.SalesCalculationThread;

@Service
public class ProductService {
	@Autowired
	ProductReposirty productReposirty;

	@Autowired
	PurchaseOrederRepository purchaseOrederRepository;

	private final List<Thread> threads = new ArrayList<>();

	public static Product productToSave = null;

	public static CountDownLatch latch;

	public static void read_modify_write(int quantity) {

		synchronized (productToSave) {
			int current = productToSave.getQuantity();
//			try {
//				Thread.sleep(10 * (int)(Math.random() * 10));
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			int newValue = current + quantity;
			ProductService.productToSave.setQuantity(newValue);
		}
	}

	public ResponseEntity<String> runThreads() {
		latch = new CountDownLatch(threads.size());
		System.out.println(latch.getCount());
		int counter = 0;
		for (Thread task : threads) {
			counter++;
			task.start();
		}
		try {
			latch.await();
		} catch (InterruptedException exp) {
			exp.printStackTrace();
		}
		int totalThreadCount = threads.size();
		threads.clear();
		productReposirty.save(productToSave);
		return ResponseEntity.ok("\n" + counter + " threads have ran sucessfully out of : " + totalThreadCount + '\n');
	}

	public ResponseEntity<String> alter_product_quantity(Integer quantity, Long p_id) {
		Optional<Product> product = productReposirty.findById(p_id);
		if (product.isPresent()) {
			if (productToSave == null) {
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

    @Async("productTaskExecutor")
    public CompletableFuture<String> alter_product_name(String name, Long p_id) {
        Optional<Product> product = productReposirty.findById(p_id);
        if (product.isPresent()) {
            product.get().setName(name);
            productReposirty.save(product.get());
            return CompletableFuture.completedFuture("Name was altered successfully");
        } else {
            return CompletableFuture.completedFuture("No such product");
        }
    }

	public static Object priceLock = new Object();

	private Server server_1 = new Server((byte) 1);

	private Server server_2 = new Server((byte) 2);

	private Server server_3 = new Server((byte) 3);

	private byte currenetServer = 1;

	public ResponseEntity<String> alter_product_price(Float price, Long p_id) {
		boolean served = false;
		// Round Robin
		switch (currenetServer) {
		case 1: {
			if (server_1.serve(productReposirty, price, p_id) != -1) {
				served = true;
				currenetServer = 2;
				break;
			}
			System.out.println("Server 1 is busy");
		}
		case 2: {
			if (server_2.serve(productReposirty, price, p_id) != -1) {
				served = true;
				currenetServer = 3;
				break;
			}
			System.out.println("Server 2 is busy");
		}
		case 3: {
			if (server_3.serve(productReposirty, price, p_id) != -1) {
				served = true;
				currenetServer = 1;
				break;
			}
			System.out.println("Server 3 is busy");
		}
		}
		if(served)
			return ResponseEntity.ok("Done !! \n");
		return  ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("Sorry all servers are busy re send your request again \n");

	}

	public void start_reports() {
		Thread backgroundJob = new Thread(new SalesCalculationThread(purchaseOrederRepository));
		backgroundJob.start();
	}

}
