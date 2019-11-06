package com.demo.springsecurityjwt.Models;

public class AuthenticationRequest {
	
	private String username;
	private String password;
	private String id;
    private String role;
    private String token;

	public AuthenticationRequest() {
	}
	
	public AuthenticationRequest(String username,String password) {
		this.username=username;
		this.password=password;
	}
		
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
