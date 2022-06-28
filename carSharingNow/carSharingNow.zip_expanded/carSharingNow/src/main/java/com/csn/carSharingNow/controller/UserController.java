package com.csn.carSharingNow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csn.carSharingNow.repositories.UserRepository;
import com.csn.carSharingNow.models.User;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userRepository.findAll();			
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(users);
		
	}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping("/all/{name:[a-z-]+}")
	public ResponseEntity<?> getUserByFirstname(@PathVariable String name) {	 
		List<User> responseUsers = new ArrayList<User>();	
		
		for(User user : userRepository.findAll() ) {
			if(user.getFirstname().toLowerCase().equals(name.toLowerCase())) {
				responseUsers.add(user);
			}			
		}
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseUsers);
		
	}
}
