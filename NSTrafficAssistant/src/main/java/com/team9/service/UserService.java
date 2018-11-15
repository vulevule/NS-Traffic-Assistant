package com.team9.service;

import org.springframework.mail.MailException;

import com.team9.dto.RegisterDto;
import com.team9.dto.UserDto;
import com.team9.model.User;

public interface UserService {

	User getUser(String username);
	boolean saveUser(User u);
	User UserDtoToUser(UserDto udto);
	User getUser(String username,String password);
	void sendNotificaitionSync(User user) throws MailException, InterruptedException;
	User RegisterDtoToUser(RegisterDto reg);
}
