package com.meatpie.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConcurrentNAV extends AbstractNAV {
	
	final double blockingCoefficient;

	public ConcurrentNAV() {
		blockingCoefficient = 0.9;
	}
	
	public ConcurrentNAV(double blockingCoefficient) {
		this.blockingCoefficient = blockingCoefficient;
	}
	
	@Override
	public double getTotalValue(Map<String, Integer> tickers) {
		Set<Entry<String,Integer>> entrySet = tickers.entrySet();
		List<Callable<Double>> tasks = new ArrayList<Callable<Double>>();
		for(Entry<String,Integer> entry : entrySet) {
			final String key = entry.getKey();
			final Integer value = entry.getValue();
			tasks.add(new Callable<Double>() {
				public Double call() throws Exception {
					return YahooFinance.getTickerPrice(key) * value;
				}
			});
		}
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int poolSize = (int) (availableProcessors / (1 - blockingCoefficient));
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		double total = 0.0;
		try {
			List<Future<Double>> results = pool.invokeAll(tasks, 10000, TimeUnit.SECONDS);
			for(Future<Double> result : results) {
				total += result.get();
			}
			pool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return total;
	}

}
