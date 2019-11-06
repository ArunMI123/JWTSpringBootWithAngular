package com.demo.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springsecurityjwt.Models.UserInfo;
import com.demo.springsecurityjwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/getUserInfo/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUserInfo(@PathVariable(name = "userId") int userId) {
		try {
			ObjectMapper objMap = new ObjectMapper();
			UserInfo user = userService.findByUserId(userId);
			return new ResponseEntity<String>(objMap.writeValueAsString(user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveAgent(@RequestBody UserInfo user) {
		try {
			ObjectMapper objMap = new ObjectMapper();
			UserInfo userObj = userService.saveUser(user);
			return new ResponseEntity<String>(objMap.writeValueAsString(userObj), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
