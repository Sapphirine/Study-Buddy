package com.e6893.education.erp.cse;

import java.io.*;
import java.net.*;


public class CustomSearchEngine {
	String Query = "big+data+analytics";
	

	public CustomSearchEngine(String str) {
		Query = str.toLowerCase();
	}
	

	public SearchResultPackage Search() {
		String API_Key = "AIzaSyAD0RBrRQ3F-Noh8c2WedXpBQqXlIxCNaY";
		String Search_Engine_ID = "002038682541700302655:bu7oifyn-te";
		Query = Query.replace(' ', '+');
		String Request =
				"https://www.googleapis.com/customsearch/v1?"
				+"key={key}"
				+"&cx={cx}"
				+"&q={q}"
				+"&start={start}";
		Request = Request
				.replace("{key}", API_Key)
				.replace("{cx}", Search_Engine_ID)
				.replace("{q}", Query);
		
		StringBuilder SearchResult = new StringBuilder();
		
		for (int i = 1; i < 30; i += 10) {
			String StartIndex = Integer.toString(i);
			String RequestUrl = Request.replace("{start}", StartIndex);
			
			HttpURLConnection Conn = null;
			String Result = null;
			try {
				URL Url = new URL(RequestUrl);
				Conn = (HttpURLConnection) Url.openConnection();
				Conn.setRequestMethod("GET"); 
				int ResultCode = Conn.getResponseCode();
				if (ResultCode == 200) {
					InputStream IS = Conn.getInputStream();
					Result = ReadAsString(IS);
					IS.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				Conn.disconnect();
			}
			if (i == 1) {
				SearchResult.append(Result);
			}
			else {
				String TenItems = ",\n" + Result.substring
						(Result.indexOf("\"items\":")+11, Result.length()-6);
				SearchResult.insert(SearchResult.length()-6, TenItems);
			}
		}
		return new SearchResultPackage(SearchResult.toString());
	}	
	
	public static String ReadAsString(InputStream Ins)
		throws IOException {
		ByteArrayOutputStream Outs = new ByteArrayOutputStream();
		byte[] Buffer = new byte[8192];
		int len = -1;
		try {
			while ((len = Ins.read(Buffer)) != -1) {
				Outs.write(Buffer, 0, len);
			}
		} finally {
			Outs.flush();
			Outs.close();
		}
		return Outs.toString();
	}
}