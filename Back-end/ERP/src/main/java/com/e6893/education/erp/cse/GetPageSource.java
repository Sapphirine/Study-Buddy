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
			HttpURLConnection Conn = (HttpURLConnection) Url.openConnection();
			int ResponseCode = Conn.getResponseCode();
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
	
}
