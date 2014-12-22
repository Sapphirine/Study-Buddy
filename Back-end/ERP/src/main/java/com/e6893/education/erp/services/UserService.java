package com.e6893.education.erp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;

import com.e6893.education.erp.dao.UserDao;
import com.e6893.education.erp.dao.impl.TopicDaoImpl;
import com.e6893.education.erp.dao.impl.UserDaoImpl;
import com.e6893.education.erp.entity.Topic;
import com.e6893.education.erp.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private TopicDaoImpl topicDaoImpl;
	
	public User getUserByUserName(String userName) {
		return userDaoImpl.getUserByUserName(userName);
	}
	
	public User userSignUp(User user) {
		return userDaoImpl.createUser(user);
	}
	
	public User userLogin(User user) {
		if (userDaoImpl.verifyUser(user))
			return user;
		return null;
	}
	
	public int addHistory(User user, Topic topic) {
		
		return userDaoImpl.addSearchedHistory(user, topic);	
	}
	
	public List<User> recommendUsers(User user, Topic topic) {
		return userDaoImpl.recommendUser(user, topic);
	}
	
	public List<Topic> recommendTopics(Topic topic) {
		return userDaoImpl.recommendTopic(topic);
	}
}
