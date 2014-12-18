package com.e6893.education.erp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e6893.education.erp.dao.impl.TopicDaoImpl;
import com.e6893.education.erp.entity.Topic;

@Service
public class TopicService {
	
	@Autowired
	private TopicDaoImpl topicDaoImpl;

	public Topic createTopic(Topic topic) {
		
		return topicDaoImpl.createTopic(topic);	
	}
}
