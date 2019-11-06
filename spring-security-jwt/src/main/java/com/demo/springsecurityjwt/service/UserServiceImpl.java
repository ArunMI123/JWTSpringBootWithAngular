package com.demo.springsecurityjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springsecurityjwt.Models.UserInfo;
import com.demo.springsecurityjwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserInfo findByUserId(int userId) {
		return userRepo.findByUserId(userId);
	}

	@Override
	public UserInfo findUserName(String userName) {
		return userRepo.findUserNameByName(userName);
	}
	
	@Override
	public UserInfo saveUser(UserInfo user) {
		return userRepo.save(user);
	}


}
