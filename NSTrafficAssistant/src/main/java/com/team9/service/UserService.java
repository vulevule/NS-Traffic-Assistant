package com.team9.service;

import com.team9.model.Passenger;

public interface UserService {
	
	Passenger getPassenger(String username);
}