package com.e6893.education.erp.cse;

import java.io.*;
import java.net.*;


public class CustomSearchEngine {
	String Query = "big+data+analytics";
	

	public CustomSearchEngine(String str) {
		Query = str.toLowerCase();
	}
	

	public SearchResultPackage Search() {
		
		String[] API_Key = new String[10];
		API_Key[0] = "AIzaSyCSROHxe282CFCxisUzItrdnE7LYXGngCI";
		API_Key[1] = "AIzaSyAD0RBrRQ3F-Noh8c2WedXpBQqXlIxCNaY";
		API_Key[2] = "AIzaSyCEjxJX1SaoNy1zuRNY1LA35yyvf2Vf8eo";
		API_Key[3] = "AIzaSyBCdgmOaBd-ygM4sqJM2TMvVkQXPGMW5PE";
		API_Key[4] = "AIzaSyASs88pk5VsMc62Oq3NWtXu-DASYnax0Uo";
		API_Key[5] = "AIzaSyAryoHy0eScXM7cUKBTVMbWmOHFqt3lwR4";
		API_Key[6] = "AIzaSyAJLaY93GD5_DlQjVdSOOhH98ZOXUHsyBM";
		API_Key[7] = "AIzaSyB5myY8cGffxQOka0C-lgV6BmIClDPEXKM";
		API_Key[8] = "AIzaSyBNBQrz5h35fmdb0zZG1UCWPB1w8XZx2mI";
		API_Key[9] = "AIzaSyBSdkZwoteGk8jimdQtdzPp6tZ4JIGkuAw";
		
		String Search_Engine_ID = "002038682541700302655:bu7oifyn-te";
		Query = Query.replace(' ', '+');
		String Request =
				"https://www.googleapis.com/customsearch/v1?"
				+"key={key}"
				+"&cx={cx}"
				+"&q={q}"
				+"&start={start}";
		
		Request = Request
				.replace("{cx}", Search_Engine_ID)
				.replace("{q}", Query);
		
		StringBuilder SearchResult = new StringBuilder();
		
		long totalStartTime = System.currentTimeMillis();
		for (int i = 1; i < 100; i += 10) {
			long totalEndTime = System.currentTimeMillis();
			if (totalEndTime - totalStartTime >= 10000) {
				System.err.println("All Custom Search APIs are unavailable.");
				break;
			}
			int api_key_choice = (int)(Math.random()*10);
			String StartIndex = Integer.toString(i);
			String RequestUrl = Request
					.replace("{key}", API_Key[api_key_choice])
					.replace("{start}", StartIndex);
			
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
			if (Result != null){
				if (i == 1) {
					SearchResult.append(Result);
				}
				else {
					String TenItems = ",\n" + Result.substring
							(Result.indexOf("\"items\":") + 11, Result.length() - 6);
					SearchResult.insert(SearchResult.length() - 6, TenItems);
				}
			}
			else {
				i -= 10;
			}
		}
		return new SearchResultPackage(SearchResult.toString());
	}	
	
	
	public static void main(String[] args) {
		// Handle cases of different input arguments.
		StringBuilder Serch_Key = new StringBuilder();
		if (args.length == 0)
			Serch_Key.append("javascript");
		else {
			for (int i = 0, n = args.length; i < n; i++) {
				if (i != 0)
					Serch_Key.append('+');
				Serch_Key.append(args[i]);
			}
		}
		
		
		
		// Establish a search instance, and conduct the search.
		long startTime = System.currentTimeMillis();
		SearchResultPackage SR = new CustomSearchEngine(Serch_Key.toString()).Search();
		long endTime = System.currentTimeMillis();
		System.out.println("Execution Time: " + (double)(endTime - startTime)/1000 + "s");
		
	
		
		// Print to screen.
//		System.out.println(SR.SearchResultInString);
		
		// Print to file "Response.txt".
		File OutputFile = new File("Response.txt");
		try {
			OutputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter OutWriter = new FileWriter(OutputFile);
			OutWriter.write(SR.SearchResultInString);
			OutWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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