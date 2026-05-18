package Threads;

import com.example.demo.Entities.Product;
import com.example.demo.Repositories.ProductReposirty;
import com.example.demo.Services.Server;

public class ServerThread implements Runnable{

	ProductReposirty productReposirty;
	Float price;
	Long p_id;
	byte serverNum;
	public boolean busy;
	public ServerThread(ProductReposirty productReposirty, Float price, Long p_id, byte serverNum) {
		this.productReposirty = productReposirty;
		this.price = price;
		this.p_id = p_id;
		this.serverNum = serverNum;
		this.busy = false;
	}
	@Override
	public void run() {
		this.busy = true;
		Product p;
		synchronized (Server.readLock) {
			p = productReposirty.findById(p_id).get();
			if(p == null)
					return;
		}
		p.setPrice(price);
		synchronized (Server.writeObject) {
			productReposirty.save(p);
		}
		this.busy = false;
		System.out.println("Serving in Server #"+serverNum);
	}
	
}
