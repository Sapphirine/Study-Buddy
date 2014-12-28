package com.e6893.education.erp.api;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.e6893.education.erp.cse.ClassificationResultPackage;
import com.e6893.education.erp.cse.Classifier;
import com.e6893.education.erp.cse.CustomSearchEngine;
import com.e6893.education.erp.cse.SearchResultPackage;
import com.e6893.education.erp.entity.Topic;
import com.e6893.education.erp.entity.User;
import com.e6893.education.erp.dao.impl.*;
import com.e6893.education.erp.services.NlpService;
import com.e6893.education.erp.services.UserService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Autowired
	TopicDaoImpl topicDaoImpl;
	
	@Autowired
	NlpService nlpService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody User userCreate(HttpServletRequest request,
			HttpServletResponse response, User user) {
		System.out.println(request.getParameterMap().toString());
		System.out.println(user.getUserName());
		return userService.userSignUp(user);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> userLogin(HttpServletRequest request,
			HttpServletResponse response, User user, BindingResult result) {
		Map<String, Object> responseBody = new HashMap<String, Object>();
		
		User loginUser = userService.userLogin(user);
		if (loginUser != null) {
			responseBody.put("user", loginUser);
			responseBody.put("status", "ok");
		}
		else {
			responseBody.put("status", "none");
		}

		return responseBody;
	}
	
	@RequestMapping(value = "/addHistory", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addHistory(HttpServletRequest request,
			HttpServletResponse response, User user, Topic topic, BindingResult result) {
		Map<String, Object> responseBody = new HashMap<String, Object>();
		
		int searchCount = userService.addHistory(user, topic);
		if (searchCount != -1) {
			responseBody.put("searchCount", searchCount);
			responseBody.put("status", "ok");
		}
		else {
			responseBody.put("status", "error");
		}

		return responseBody;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(HttpServletRequest request,
			HttpServletResponse response, BindingResult result) {
		User user = new User();
		user.setUserName("Sheldon");
		user.setPwd("S");
		
		Topic topic = new Topic();
		topic.setTopicName("calculus");
		
		List<User> users = userDaoImpl.recommendUser(user, topic);
		List<Topic> topics = userDaoImpl.recommendTopic(topic);
		for (User u: users) {
			System.out.println(u.toString());
		}
		System.out.println("========================================================");
		for (Topic t: topics) {
			System.out.println(t);
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> classification(HttpServletRequest request,
			HttpServletResponse response, User user) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> responseBody = new HashMap<String, Object>();
		String keyWord = request.getParameter("query");
		CustomSearchEngine CSE = 
				new CustomSearchEngine(keyWord);
		SearchResultPackage SRP = CSE.Search();

		ClassificationResultPackage CRP = new Classifier().classify(SRP);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(CRP);
   
        responseBody.put("searchResults", json);
        
        List<String> topics = nlpService.getTopics(keyWord);

        Topic topic = new Topic();
		topic.setTopicName(topics.get(0));
		topicDaoImpl.createTopic(topic);
		userDaoImpl.addSearchedHistory(user, topic);
		List<User> users = userDaoImpl.recommendUser(user, topic);
		
		responseBody.put("recommendedUsers", users);

		return responseBody;
		


	}

}
