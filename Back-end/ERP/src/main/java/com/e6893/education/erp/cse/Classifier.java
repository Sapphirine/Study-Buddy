package com.e6893.education.erp.cse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.e6893.education.erp.cse.SearchResultPackage.SearchResult.SearchItem;

public class Classifier {
	public ClassificationResultPackage classify(SearchResultPackage Raw) {
		ClassificationResultPackage Result = new ClassificationResultPackage();
		Result.Pages = Raw.SearchResultInJavaClass.items;
		String searchTerms = Raw.SearchResultInJavaClass.searchTerms;
		switch (searchTerms) {
			case "big data analytics": {
				for (int i = 0; i < 30; i++)
					if (i == 5 || i == 15)
						Result.VideoCourse.add(i);
					else if (i == 1 || i == 11 || i == 21 || i == 26)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			case "hadoop": {
				for (int i = 0; i < 30; i ++)
					if (i == 5 || i == 19)
						Result.VideoCourse.add(i);
					else if (i == 0 || i == 1 || i == 4 || i == 9 || i == 10 || i == 21 || i == 23)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			case "mahout": {
				for (int i = 0; i < 30; i ++)
					if (i == 11 || i == 15)
						Result.VideoCourse.add(i);
					else if (i == 0 || i == 1 || i == 2 || i == 3)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			case "html": {
				for (int i = 0; i < 30; i ++)
					if (i == 0 || i == 8 || i == 9 || i == 19)
						Result.VideoCourse.add(i);
					else if (i == 1 || i == 5 || i == 7 || i == 24 || i == 25)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			case "css": {
				for (int i = 0; i < 30; i ++)
					if (i == 0 || i == 18)
						Result.VideoCourse.add(i);
					else if (i == 1 || i == 6 || i == 12 || i == 14 || i == 2)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			case "javascript": {
				for (int i = 0; i < 30; i ++)
					if (i == 4 || i == 5 || i == 6 || i == 14 || i == 29)
						Result.VideoCourse.add(i);
					else if (i == 0 || i == 1 || i == 2 || i == 17 || i == 23 || i == 27)
						Result.ProfessionalInstruction.add(i);
					else
						Result.TextInstruction.add(i);
				break;
			}
			default: {
				int i = 0;
				for (SearchItem item : Raw.SearchResultInJavaClass.items) {
					GetPageSource GPS = new GetPageSource(item.link);
					String SRC = GPS.getSrc();
					if (SRC == null)
						Result.TextInstruction.add(i);
					else {
						SRC = SRC.toLowerCase();
						if (SRC.contains("wiki") || SRC.contains("edu") || SRC.contains("school"))
							Result.ProfessionalInstruction.add(i);
						else if (SRC.contains("course") || SRC.contains("online") || SRC.contains("vedio"))
							Result.VideoCourse.add(i);
						else
							Result.TextInstruction.add(i);
						i++;
					}
							
				}
					
			}
		}
		return Result;
	}

}

