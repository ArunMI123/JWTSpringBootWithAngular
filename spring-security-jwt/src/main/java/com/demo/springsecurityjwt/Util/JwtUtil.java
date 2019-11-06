package com.demo.springsecurityjwt.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.springsecurityjwt.Models.AuthenticationRequest;
import com.demo.springsecurityjwt.Models.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	private String SECRET_KEY="secret";
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims= new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}
	
	public String createToken(Map<String,Object> claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +1000 * 60 * 60 * 10)) //10hours is set as expiry
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	
	//LDAP Token Generation
	public String generateToken(AuthenticationRequest userDetails) {
		Map<String,Object> claims= new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}

	//DB Token Generation
	public String generateToken(UserInfo user) {
		Map<String,Object> claims= new HashMap<>();
		return createToken(claims,user.getUserName());
	}
	
	//DB Validate Token Based on UserInfo Entity
	public Boolean validateToken(String token, UserInfo userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
	}

}
