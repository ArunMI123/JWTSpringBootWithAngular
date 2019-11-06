package com.demo.springsecurityjwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.springsecurityjwt.Models.UserInfo;
import com.demo.springsecurityjwt.Util.JwtUtil;
import com.demo.springsecurityjwt.service.MyUserDetailsService;
import com.demo.springsecurityjwt.service.UserService;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader=request.getHeader("Authorization");
		String username=null;
		String jwt=null;
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			jwt=authorizationHeader.substring(7);
			
			if(!jwt.equals("null")) {
				username=jwtUtil.extractUsername(jwt);
			}
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			UserInfo userDetails = userService.findUserName(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				//UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,null);
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
