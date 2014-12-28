package com.e6893.education.erp.cse;

import java.io.*;
import java.util.*;

import org.json.*;



public class SearchResultPackage {
	public String SearchResultInString;
	public SearchResult SearchResultInJavaClass;
	
	SearchResultPackage(String str) {
		SearchResultInString = str;
		SearchResultInJavaClass = new SearchResult(str);
	}
	
	
	class SearchResult {
		public String searchTerms;
		public ArrayList<SearchItem> items;
		
		SearchResult(String str) {
			JSONObject obj = new JSONObject(str);
			JSONObject queries = (JSONObject)obj.get("queries");
			JSONObject requst = (JSONObject)((JSONArray)queries.get("request")).get(0);
			searchTerms = requst.optString("searchTerms");

			JSONArray ary = new JSONArray(obj.optString("items"));
			items = new ArrayList<SearchItem>();
			for (int i = 0; i < ary.length(); i++) {
				items.add(new SearchItem(ary.optString(i)));
			}
		}
	
		
		class SearchItem {
			public String title;
			public String link;
			public String snippet;
			
			SearchItem(String str) {
				JSONObject obj = new JSONObject(str);
				title = obj.optString("title");
				link = obj.optString("link");
				snippet = obj.optString("snippet");
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		String SearchResultInString = null;
		
		try {
			FileInputStream fis = new FileInputStream("Response.txt");
			try {
				byte[] buf = new byte[fis.available()];
				fis.read(buf);
				SearchResultInString = new String(buf);
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		SearchResultPackage SR = new SearchResultPackage(SearchResultInString);
		
		System.out.println(SearchResultInString);
		
		List<String> AllPageSrcs = new ArrayList<String>();
		for (SearchResultPackage.SearchResult.SearchItem item
				: SR.SearchResultInJavaClass.items) {
			if (item.link != null) {
				GetPageSource GPS = new GetPageSource(item.link);
				AllPageSrcs.add(GPS.getSrc());
			}
			
		}
		
		System.out.println(SR.SearchResultInJavaClass.searchTerms);
		
		for (String str : AllPageSrcs) {
			System.out.println(str);
		}
		
		System.out.println("Done.");
	}
}