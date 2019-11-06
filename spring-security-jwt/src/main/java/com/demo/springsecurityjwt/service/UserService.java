package com.demo.springsecurityjwt.service;

import com.demo.springsecurityjwt.Models.UserInfo;

public interface UserService {

	public UserInfo findByUserId(int userId);
	
	public UserInfo findUserName(String userName);

	public UserInfo saveUser(UserInfo user);

}
