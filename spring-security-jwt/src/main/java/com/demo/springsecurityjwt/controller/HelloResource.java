package com.demo.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@RequestMapping(value = "/db/login", method = RequestMethod.POST)
	public ResponseEntity<?> LoginWithDB(@RequestBody UserInfo user)
			throws Exception {
		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//					user.getUserName(), user.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserInfo userDetails = userService.findUserName(user.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
