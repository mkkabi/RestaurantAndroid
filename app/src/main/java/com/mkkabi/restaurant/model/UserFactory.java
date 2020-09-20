package com.mkkabi.restaurant.model;

import java.util.Map;

// A factory method class that creates User instances - Client and Staff User classes
public class UserFactory{
	private static UserFactory instance;
	private User user;
	
	private UserFactory(){}

	private static class UserFactoryHelper {
		private static final UserFactory instance = new UserFactory();
	}

	public static UserFactory getInstance(User user) {
		UserFactoryHelper.instance.setUser(user);
		return UserFactoryHelper.instance;
	}
	
	public static UserFactory getInstance() {
		return UserFactoryHelper.instance;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public void destroyUser(){
		this.user = null;
	}
}