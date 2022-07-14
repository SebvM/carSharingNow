package com.csn.carSharingNow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.csn.carSharingNow.models.User;
import com.csn.carSharingNow.repositories.UserRepository;
import com.csn.carSharingNow.security.SecurityService;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepostiory;
	@Autowired
	SecurityService securityService;
	public User getCurrenUser() {
		User user = securityService.get().get();
		
		return user;		
	}
	
	public void addUser(User user) {
		userRepostiory.save(user);			
	}
}
