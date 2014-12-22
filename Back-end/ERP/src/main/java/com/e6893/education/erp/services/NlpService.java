package com.e6893.education.erp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.e6893.education.erp.entity.Topic;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

@Service
public class NlpService {
	
	static Set<String> nounPhrases = new HashSet<>();
	
	@Autowired
	ServletContext servletContext;
	
	public List<Topic> getTopics(String searchSentence) {
		InputStream modelInParse = null;
		try {
			File modelDir = new File( servletContext.getRealPath("/WEB-INF/config/en-parser-chunking.bin") );
			modelInParse = new FileInputStream(modelDir); //from http://opennlp.sourceforge.net/models-1.5/
			ParserModel model = new ParserModel(modelInParse);
			
			//create parse tree
			Parser parser = ParserFactory.create(model);
			Parse topParses[] = ParserTool.parseLine(searchSentence, parser, 1);
			
			//call subroutine to extract noun phrases
			for (Parse p : topParses)
				getNounPhrases(p);

			if (nounPhrases.isEmpty())
				nounPhrases.add(searchSentence);
			
			List<Topic> topics = new ArrayList<Topic>();
			for (String topicName : nounPhrases) {
				Topic topic = new Topic();
				topic.setTopicName(topicName);
				topics.add(topic);
			}
			
			return topics;
			
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelInParse != null) {
		    try {
		    	modelInParse.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		
		
		return null;
	}
	
	//recursively loop through tree, extracting noun phrases
	public static void getNounPhrases(Parse p) {
	    if (p.getType().equals("NP") || p.getType().equals("NN") || p.getType().equals("NNS")) { //NP=noun phrase
	         nounPhrases.add(p.getCoveredText());
	    }
	    for (Parse child : p.getChildren())
	         getNounPhrases(child);
	}

	



	
}
