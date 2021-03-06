package com.team9.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;


import com.team9.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByUsername(String username);
	User findByUsernameAndPassword(String username,String password);

	
}
