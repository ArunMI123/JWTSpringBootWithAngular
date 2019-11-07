package com.demo.springsecurityjwt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springsecurityjwt.Models.AuthenticationRequest;
import com.demo.springsecurityjwt.Models.AuthenticationResponse;
import com.demo.springsecurityjwt.Models.UserInfo;
import com.demo.springsecurityjwt.Util.JwtUtil;
import com.demo.springsecurityjwt.service.LdapAuthendicationService;
import com.demo.springsecurityjwt.service.MyUserDetailsService;
import com.demo.springsecurityjwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin
public class HelloResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	LdapAuthendicationService ldapAuthendicationService;
	
	@Autowired
	UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping({ "/hello" })
	public String HelloWorld() {
		return "Hello";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect username or password", e);
		}
		// above code will be executed only if credentials are correct.
		// incase if it is wrong then throws exception.

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	//LDAP Login Service
	@RequestMapping(value = "/ldap/login", method = RequestMethod.POST)
	public AuthenticationRequest Login(@RequestBody AuthenticationRequest user){
		AuthenticationRequest jwtUserDto = null;
		jwtUserDto=ldapAuthendicationService.getUSerDetails(user);
		return jwtUserDto;
	}
	
	
//	@RequestMapping(value = "/db/login", method = RequestMethod.POST)
//	public ResponseEntity<?> LoginWithDB(@RequestBody UserInfo user)
//			throws Exception {
//		try {
////			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
////					user.getUserName(), user.getPassword()));
//		} catch (Exception e) {
//			throw new Exception("Incorrect username or password", e);
//		}
//
//		final UserInfo userDetails = userService.findUserName(user.getUserName());
//		final String jwt = jwtUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new AuthenticationResponse(jwt));
//	}
	
	@RequestMapping(value = "/db/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> userLogin(@RequestBody UserInfo user) {
		try {
			ObjectMapper objMap = new ObjectMapper();
			UserInfo userObject = userService.findUserName(user.getUserName());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (userObject != null) {
				if (userObject.getUserName().equals(user.getUserName())
						&& userObject.getPassword().equals(user.getPassword())) {
					resultMap.put("status", "Success");
					resultMap.put("user", userObject);
					final String jwt = jwtUtil.generateToken(userObject);
					resultMap.put("jwt", new AuthenticationResponse(jwt));
				} else {
					resultMap.put("status", "Error");
					resultMap.put("statusText", "Given Password is wrong");
				}
			} else {
				resultMap.put("status", "Error");
				resultMap.put("statusText", "Given UserId is wrong");
			}
			return new ResponseEntity<String>(objMap.writeValueAsString(resultMap), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
