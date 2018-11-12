package com.team9.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.team9.service.UserService;



public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	//TokenUtils tokenUtils;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

}
