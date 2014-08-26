package com.meatpie.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNAV {

	/**
	 * 从stocks.txt文件中读取股票信息（股票代码，持有数目）
	 * @return
	 * @throws IOException
	 */
	public Map<String, Integer> readTickers() throws IOException {
		Map<String, Integer> tickers = new HashMap<String, Integer>();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("stocks.txt")));
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] datas = line.split(",");
			tickers.put(datas[0], Integer.valueOf(datas[1]));
		}
		return tickers;
	}
	
	public void computeTotalValue() throws IOException {
		final long start = System.nanoTime();
		Map<String, Integer> tickers = readTickers();
		double totalValue = getTotalValue(tickers);
		final long end = System.nanoTime();
		System.out.println("compute total value is " + totalValue + ", spend " + (end - start) / 1.0e9 + " second(s)");
	}
	
	public abstract double getTotalValue(Map<String, Integer> tickers);
}
