package com.e6893.education.erp.api;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.e6893.education.erp.services.NlpService;

@Controller
public class TestController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	NlpService nlpService;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/nlp/{searchSentence}", method = RequestMethod.GET)
	public String test(@PathVariable String searchSentence) {
		System.out.println(nlpService.getTopics(searchSentence));
		
		return "showMessage";
	}

}
