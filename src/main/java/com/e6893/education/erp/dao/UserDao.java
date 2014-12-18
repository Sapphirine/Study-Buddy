package com.e6893.education.erp.dao;

import com.e6893.education.erp.entity.User;

public interface UserDao {
	public User getUserByUserName(String userName);
	
	public User createUser(User user);
	
	public User updateUser(User user);
	
	// public int deleteUser(long userId);
	
	
}
