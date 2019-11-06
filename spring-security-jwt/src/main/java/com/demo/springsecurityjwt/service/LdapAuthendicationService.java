package com.demo.springsecurityjwt.service;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springsecurityjwt.Models.AuthenticationRequest;
import com.demo.springsecurityjwt.Util.JwtUtil;

@Service
public class LdapAuthendicationService {

	public LdapContext ctx = null;
	SearchControls cons = new SearchControls();
	
	@Autowired
	JwtUtil jwtTokenGenerator;
	
	 private void getLdapContext() {
	        
	        try {
	            Hashtable<String, String> env = new Hashtable<String, String>();
	            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
	            env.put(Context.SECURITY_PRINCIPAL, "CN=Gouravverma Manmohan,OU=Project,OU=Main Office,DC=ksplad,DC=com");//input user & password for access to ldap
	            env.put(Context.SECURITY_CREDENTIALS, "Nov@1234");
	            env.put(Context.PROVIDER_URL, "ldap://172.24.0.12:389");
	            env.put(Context.REFERRAL, "follow");
	            ctx = new InitialLdapContext(env, null);
	        } catch (NamingException nex) {
	            nex.printStackTrace();
	        }
	    }
	 
	 	public AuthenticationRequest getUSerDetails(AuthenticationRequest user){
	 		AuthenticationRequest JwtUserDto = null;
	 		this.getLdapContext();
	 		this.getSearchControls();
	 		JwtUserDto=getUserInfo(ctx, cons, user);
			return JwtUserDto ;
	 		
	 	}

	    public  AuthenticationRequest getUserInfo( LdapContext ctx, SearchControls searchControls,AuthenticationRequest userInfo) {
	    	AuthenticationRequest ldapdetails= new AuthenticationRequest();
	    	ArrayList<String> userdetails = new ArrayList<>();
	    	try {
	            NamingEnumeration<SearchResult> answer = ctx.search("DC=ksplad,DC=com",  "sAMAccountName="+userInfo.getUsername(), searchControls);
	            while (answer.hasMoreElements())
				{
					SearchResult result = (SearchResult) answer.next();
					Attributes attribs = result.getAttributes();
	 
					if (null != attribs)
					{
						for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();)
						{
							Attribute atr = (Attribute) ae.next();
							userdetails.add(atr.get().toString());
							
						}
					}
				}
	           
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    	if(!userdetails.isEmpty()){
	    		ldapdetails.setUsername(userdetails.get(0));
	    		ldapdetails.setId(userdetails.get(1));
	    	}
	    	String token=jwtTokenGenerator.generateToken(ldapdetails);
	    	System.out.println(token);
	    	ldapdetails.setToken(token);
	        return ldapdetails;
	    }

	    private void getSearchControls() {
	        
	        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        String[] attrIDs = {"name","sAMAccountName"};
	        cons.setReturningAttributes(attrIDs);
	    }
}
