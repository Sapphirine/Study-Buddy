package com.e6893.education.erp.entity;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;


//@NodeEntity
public class User {
//	@GraphId
//	private Long nodeId;
//	
//	@Indexed(unique=true)
	private String userName;
	
	private String pwd;		//password


//	@Fetch
//	@RelatedTo(type = "SEARCHED", direction = Direction.OUTGOING)
//	private Set<Topic> interestedTopics;
//	
//	@Fetch
//	@RelatedToVia(type = "SEARCHED", direction = Direction.OUTGOING)
//	private Set<Interest> interests;
//
//	public void searchTopic(Topic topic) {
//		
//	}
//
//	public Long getNodeId() {
//		return nodeId;
//	}
//
//	public void setNodeId(Long nodeId) {
//		this.nodeId = nodeId;
//	}
//
//	public Set<Topic> getInterestedTopics() {
//		return interestedTopics;
//	}
//
//	public void setInterestedTopics(Set<Topic> interestedTopics) {
//		this.interestedTopics = interestedTopics;
//	}
//
//	public Set<Interest> getInterests() {
//		return interests;
//	}
//
//	public void setInterests(Set<Interest> interests) {
//		this.interests = interests;
//	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
