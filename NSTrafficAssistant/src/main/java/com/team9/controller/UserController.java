package com.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.UserDto;
import com.team9.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(
			value = "/user/create", 
			method=RequestMethod.POST, 
			consumes="application/json")
	public ResponseEntity<String> addUser(@RequestBody UserDto user){
		
		if(userService.getUser(user.getUsername()) != null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		userService.saveUser(userService.UserDtoToUser(user));
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

}
