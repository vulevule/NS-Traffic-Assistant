package com.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.model.Passenger;
import com.team9.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userReporitory;
	
	@Override
	public Passenger getPassenger(String username) {
		// TODO Auto-generated method stub
		return userReporitory.findUserByUsername(username);
	}

	@Override
	public Passenger savePassenger(Passenger pas) {
		// TODO Auto-generated method stub
		return userReporitory.save(pas);
	}

	@Override
	public Passenger getPassenger(String username, String password) {
		// TODO Auto-generated method stub
		return userReporitory.findUserByUsernameAndPassword(username, password);
	}

	
}
