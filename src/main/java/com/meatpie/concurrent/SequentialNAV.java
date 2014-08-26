package com.meatpie.concurrent;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SequentialNAV extends AbstractNAV {

	@Override
	public double getTotalValue(Map<String, Integer> tickers) {
		double total = 0.0;
		Set<Entry<String,Integer>> entrySet = tickers.entrySet();
		for(Entry<String,Integer> entry : entrySet) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			double price = 0.0;
			try {
				price = YahooFinance.getTickerPrice(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
			total += price * value;
		}
		return total;
	}
}
