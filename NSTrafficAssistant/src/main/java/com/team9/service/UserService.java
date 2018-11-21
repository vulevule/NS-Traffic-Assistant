package com.team9.service;

import org.springframework.mail.MailException;


import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.model.User;

public interface UserService {

	User getUser(String username);
	User UpdateDtoToUser(UpdateProfileDto update);
	boolean saveUser(User u);
	User UserDtoToUser(UserDto udto);
	User getUser(String username,String password);
	void sendNotificaitionSync(UserDto user) throws MailException, InterruptedException;
	
}
