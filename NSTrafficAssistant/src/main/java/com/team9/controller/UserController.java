package com.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.LoginDto;
import com.team9.dto.RegisterDto;
import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.model.Role;
import com.team9.model.User;
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
	
	
	@RequestMapping(
			value="/user/login",
			method=RequestMethod.POST,
			consumes="application/json")
	public ResponseEntity<User> login(@RequestBody LoginDto log){
		
		try {
		
		User user=userService.getUser(log.getUsername(), log.getPassword());
		if (user==null) {
			
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
		}catch(Exception ex) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		}
	
	@RequestMapping(
			value="/user/register",
			method=RequestMethod.POST,
			consumes="application/json")
	public ResponseEntity<User> register(@RequestBody RegisterDto reg){
		try {
			
			User u=userService.getUser(reg.getUserName());
			if (u!=null) {
				return new ResponseEntity<>(null,HttpStatus.CONFLICT);
			}
			User user=userService.RegisterDtoToUser(reg);
			userService.saveUser(user);
			userService.sendNotificaitionSync(user);
			
			return new ResponseEntity<User>(u,HttpStatus.CREATED);
			
		}catch(Exception ex) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			
		}
		
		
		
		
	}
	
	
	@RequestMapping(value="/user/profileUpdate",method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<User> updateStudent(@RequestBody UpdateProfileDto profileDTO){
		
		User user = userService.UpdateDtoToUser(profileDTO); 
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		boolean updated=userService.saveUser(user);
		
		if(updated==true) {
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
		
		}
	
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

}
}