package com.team9.service;

import java.util.ArrayList;

import org.springframework.mail.MailException;


import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.dto.ValidationDTO;
import com.team9.exceptions.UserNotFoundException;
import com.team9.model.User;
import com.team9.model.Passenger;

public interface UserService {

	User getUser(String username);
	User UpdateDtoToUser(UpdateProfileDto update);
	boolean saveUser(User u);
	User UserDtoToUser(UserDto udto);
	User getUser(String username,String password);
	//void sendNotificaitionSync(UserDto user) throws MailException, InterruptedException;
	ArrayList<Passenger> readyToValidate();
	Passenger validationProcess(ValidationDTO val) throws UserNotFoundException;
	boolean SaveUpdated(User u);
}
