package com.team9.repository;

import org.springframework.data.repository.CrudRepository;

import com.team9.model.Passenger;
import com.team9.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	Passenger findUserByUsername(String username);
	Passenger findUserByUsernameAndPassword(String username,String password);
	Passenger save(Passenger pas);
	
}
