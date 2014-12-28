package com.e6893.education.erp.cse;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.e6893.education.erp.cse.SearchResultPackage.SearchResult.SearchItem;



public class Classifier {
	
	double[] ProfessionalInstructionStdVec = {0.366993942, 0.36172713, 0.055165483, 
			0.161045196, 0.00029068, 0.187410079, 0.049950187, 0.04182394, 0.000990921};
	double[] OnlineVideoCourseStdVec = {0.070642927, 0.001167158, 0.00029179, 0.086677968, 
			0, 0.033699276, 0.825919622, 0.045305761, 0.205804121};
	double[] GeneralDescriptionStdVec = {};
	
	
	
	public ClassificationResultPackage classify(SearchResultPackage Raw) {
		ClassificationResultPackage Result = new ClassificationResultPackage();
		String searchTerms = Raw.SearchResultInJavaClass.searchTerms;
		switch (searchTerms) {
//			case "big data analytics": {
//				for (int i = 0; i < 30; i++)
//					if (i == 5 || i == 15)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 1 || i == 11 || i == 21 || i == 26)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
//			case "hadoop": {
//				for (int i = 0; i < 30; i ++)
//					if (i == 5 || i == 19)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 0 || i == 1 || i == 4 || i == 9 || i == 10 || i == 21 || i == 23)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
//			case "mahout": {
//				for (int i = 0; i < 30; i ++)
//					if (i == 11 || i == 15)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 0 || i == 1 || i == 2 || i == 3)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
//			case "html": {
//				for (int i = 0; i < 30; i ++)
//					if (i == 0 || i == 8 || i == 9 || i == 19)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 1 || i == 5 || i == 7 || i == 24 || i == 25)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
//			case "css": {
//				for (int i = 0; i < 30; i ++)
//					if (i == 0 || i == 18)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 1 || i == 6 || i == 12 || i == 14 || i == 2)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
//			case "javascript": {
//				for (int i = 0; i < 30; i ++)
//					if (i == 4 || i == 5 || i == 6 || i == 14 || i == 29)
//						Result.VideoCourse.add(Raw.SearchResultInJavaClass.items.get(i));
//					else if (i == 0 || i == 1 || i == 2 || i == 17 || i == 23 || i == 27)
//						Result.ProfessionalInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//					else
//						Result.TextInstruction.add(Raw.SearchResultInJavaClass.items.get(i));
//				break;
//			}
			default: {
				long totalStartTime = System.currentTimeMillis();
				for (SearchItem item : Raw.SearchResultInJavaClass.items) {
					long totalEndTime = System.currentTimeMillis();
					String url = item.link;
					if (((Result.ProfessionalInstruction.size() >= 5)
							&& (Result.OnlineVideoCourse.size() >= 5)
							&& (Result.GeneralDescription.size() >= 5))
							|| (totalEndTime - totalStartTime >= 20000))
						break;
					if (url.contains("khanacademy.org") || url.contains("coursera.org") ||
							url.contains("edx.org") || url.contains("udemy.org") || 
									url.contains("codecademy.com") || url.contains("youtube.com"))
								Result.OnlineVideoCourse.add(item);
					else if (url.contains("edu") || url.contains("wiki") || url.contains("school"))
						Result.ProfessionalInstruction.add(item);
					else {
						String SRC = new GetPageSource(url).getSrc();
						if (SRC != null) {
							double[] vec = vectorizeSrc(SRC, "normalize");
							double[] angle = new double[2];
							angle[0] = getCosineDistance(vec, ProfessionalInstructionStdVec);
							angle[1] = getCosineDistance(vec, OnlineVideoCourseStdVec);
							if (angle[0] < 30)
								Result.ProfessionalInstruction.add(item);
							else if (angle[1] < 30)
								Result.OnlineVideoCourse.add(item);
							else if (angle[0] < 60 || angle[1] < 60)
								Result.GeneralDescription.add(item);
						}
					}
				}
			}
		}
		return Result;
	}

	public int getCount(String Str, String subStr) {
		if ((Str != null) && (subStr != null)) {
			int count = 0, start = 0;
		    while((start = Str.indexOf(subStr,start))>=0){
		        start += subStr.length();
		        count ++;
		    }
		    return count;
		}
		else return -1;
	}
	
	public double getCosineDistance(double[] vec1, double[] vec2) {
		double innerProduct = 0;
		double vec1Norm = 0;
		double vec2Norm = 0;
		for (int i = 0; i < Math.min(vec1.length,vec2.length); i++) {
			innerProduct += vec1[i]*vec2[i];
			vec1Norm += vec1[i]*vec1[i];
			vec2Norm += vec2[i]*vec2[i];
		}
		return Math.acos(innerProduct/Math.sqrt(vec1Norm*vec2Norm))*180/Math.PI;
		
	}
	
	public double[] vectorizeSrc(String src, String option) {
		double[] result = new double[9];
		
		result[0] = getCount(src, "tutorial");
		result[1] = getCount(src, "wiki");
		result[2] = getCount(src, "documentation");
		result[3] = getCount(src, "contents");
		result[4] = getCount(src, "appendices");
		result[5] = getCount(src, "edu");
		result[6] = getCount(src, "course");
		result[7] = getCount(src, "school");
		result[8] = getCount(src, "video");
		
		if (option == "normalize") {
			double norm = 0;
			for (int i = 0; i < 9; i++)
				norm += result[i]*result[i];
			norm /= Math.sqrt(norm);
			for (int i = 0; i < 9; i++)
				result[i] /= norm;
		}
		
		return result;
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
		
		
		SearchResultPackage SRP = new SearchResultPackage(SearchResultInString);
		
		
		long startTime = System.currentTimeMillis();
		ClassificationResultPackage CRP = new Classifier().classify(SRP);
		long endTime = System.currentTimeMillis();
		System.out.println("Execution Time: " + (double)(endTime - startTime)/1000 + "s");
		
		System.out.println(CRP.OnlineVideoCourse);
		System.out.println(CRP.ProfessionalInstruction);
		System.out.println(CRP.GeneralDescription);
	}
	
}

