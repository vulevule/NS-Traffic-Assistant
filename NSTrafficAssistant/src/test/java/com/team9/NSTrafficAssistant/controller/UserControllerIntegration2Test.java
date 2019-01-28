package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegration2Test {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Test
	@Transactional
	@Rollback(true)
	public void test_loginAdmin() {
		
		ResponseEntity<LoginUserDto> result=restTemplate.postForEntity("/user/login",
				new LoginDto("laralukic", "7777"), LoginUserDto.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().getRole().equalsIgnoreCase("ADMIN"));
		

}}
