package com.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.LoginDto;
import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.model.Role;
import com.team9.model.User;
import com.team9.security.TokenUtils;
import com.team9.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(value = "/user/create", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addUser(@RequestBody UserDto user) {
		try {

			if (userService.getUser(user.getUsername()) != null) {
				return new ResponseEntity<String>(HttpStatus.CONFLICT);
			}
			userService.saveUser(userService.UserDtoToUser(user));
			if (user.getRole().equalsIgnoreCase("PASSENGER")) {

				userService.sendNotificaitionSync(user);
			}

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception ex) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody LoginDto log) {

		try {

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(log.getUsername(),
					log.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(log.getUsername());
			return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);

			/*
			 * User user=userService.getUser(log.getUsername(),
			 * log.getPassword()); if (user==null) {
			 * 
			 * return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND); }
			 * return new ResponseEntity<User>(user, HttpStatus.OK);
			 */

		} catch (Exception ex) {

			return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user/profileUpdate", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<User> updateStudent(@RequestBody UpdateProfileDto profileDTO) {

		User user = userService.UpdateDtoToUser(profileDTO);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean updated = userService.saveUser(user);

		if (updated == true) {
			return new ResponseEntity<User>(user, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
}