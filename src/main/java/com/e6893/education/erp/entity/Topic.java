package com.e6893.education.erp.entity;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.neo4j.graphdb.Direction;

//@NodeEntity
public class Topic {
	
//	@GraphId
//	private Long nodeId;
//	
//	@Indexed(unique=true)
	private String topicName;
	
//	@Fetch
//	@RelatedTo(type = "SEARCHED", direction = Direction.INCOMING)
//	Set<User> users;
//
//	@RelatedToVia(type = "SEARCHED", direction = Direction.INCOMING)
//	private Set<Interest> interests;
//	
	

//	public Long getNodeId() {
//		return nodeId;
//	}
//
//
//	public void setNodeId(Long nodeId) {
//		this.nodeId = nodeId;
//	}


	public String getTopicName() {
		return topicName;
	}


	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


//	public Set<User> getUsers() {
//		return users;
//	}
//
//
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}
//
//
//	public Set<Interest> getInterests() {
//		return interests;
//	}
//
//
//	public void setInterests(Set<Interest> interests) {
//		this.interests = interests;
//	}

	
	
}
