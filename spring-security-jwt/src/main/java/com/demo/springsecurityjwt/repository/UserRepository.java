package com.demo.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.springsecurityjwt.Models.UserInfo;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer>{

	UserInfo findByUserId(int userId);

	@Query("select u from UserInfo u where u.userName = :userName")
	UserInfo findUserNameByName(String userName);

}
