package com.demo.springsecurityjwt.Models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="user_info")
public class UserInfo {

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id_gen")
	@TableGenerator(name = "user_id_gen",
    table = "SEQUENCES",
    pkColumnName = "SEQ_NAME",
    valueColumnName = "SEQ_NUMBER",
    pkColumnValue = "USER_SEQ_ID",
    allocationSize=1)
	private int userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
