package com.example.demo.Services;

import java.util.ArrayList;

import com.example.demo.Repositories.ProductReposirty;

import Threads.ServerThread;

public class Server {
	private byte server_number = 0;

	private final int CAPACITY = 50;
	private ArrayList<ServerThread> threads = new ArrayList<ServerThread>(CAPACITY);

	public Server(byte num) {
		server_number = num;
		for (int i = 0; i < CAPACITY; i++) {
			threads.add(i, null);
		}
	}

	private int alocateThread() {
		int i = 0;
		for (; i < CAPACITY; i++) {
			if (threads.get(i) == null) {
				return i;
			}
		}
		return -1;
	}

	public static Object readLock = new Object();

	public static Object writeObject = new Object();

	private void freeThreads() {
		for(int i = 0; i < CAPACITY; i++) {
			if(threads.get(i) != null) {
				if(!threads.get(i).busy) {
					threads.set(i, null);
				}
			}
		}
	}
	public byte serve(ProductReposirty productReposirty, Float price, Long p_id) {
		int i = alocateThread();
		if (i == -1)
			return (byte) i;
		ServerThread thread = new ServerThread(productReposirty, price, p_id, server_number);
		threads.add(i, thread);
		new Thread(threads.get(i)).start();
		freeThreads();
		return server_number;
	}
}
