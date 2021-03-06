package com.e6893.education.erp.cse;

import java.io.*;
import java.net.*;

public class GetPageSource {
	String url;
	
	GetPageSource(String str) {
		url = str;
	}
	
	String getSrc() {
		String Source = null;
		try {
			URL Url = new URL(url);
			long startTime = System.currentTimeMillis();
			HttpURLConnection Conn = (HttpURLConnection) Url.openConnection();
			int ResponseCode = Conn.getResponseCode();
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + "ms\t" + url);
			if (ResponseCode == 200) {
				InputStream IS = Conn.getInputStream();
				Source =  CustomSearchEngine.ReadAsString(IS);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Source;
	}
	
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++)
			new GetPageSource("http://www.google.com/").getSrc();
		long endTime = System.currentTimeMillis();
		System.out.println("Execution Time: " + (double)(endTime - startTime)/1000 + "s");
		//System.out.println(SRC);
	}
}
