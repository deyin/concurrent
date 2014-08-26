package com.meatpie.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class YahooFinance {
	
	/**
	 * 计算股票价格
	 * @param ticker 股票名称
	 * @return
	 * @throws IOException 
	 */
	public static double getTickerPrice(String ticker) throws IOException {
		final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + ticker);
		InputStream in = url.openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		reader.readLine(); // ignore header
		String line = reader.readLine();
		String[] data = line.split(",");
		return Double.valueOf(data[data.length - 1]);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(getTickerPrice("BAC"));
	}

}
