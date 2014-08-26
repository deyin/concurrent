package com.meatpie.concurrent;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
	
	public void execute() throws IOException {
		AbstractNAV nav = null;
		nav = new SequentialNAV();
		nav.computeTotalValue();
		
		System.out.println("===========================");
		
		nav = new ConcurrentNAV();
		nav.computeTotalValue();
		
		System.out.println("===========================");
		
		nav = new ConcurrentNAV(0.8);
		nav.computeTotalValue();
		
	}
	
	public static void main(String[] args) throws IOException {
		new App().execute();
	}
}
