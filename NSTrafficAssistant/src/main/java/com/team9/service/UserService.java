package com.team9.service;

import com.team9.dto.UserDto;
import com.team9.model.User;

public interface UserService {

	User getUser(String username);
	boolean saveUser(User u);
	User UserDtoToUser(UserDto udto);
	User getUser(String username,String password);
}
