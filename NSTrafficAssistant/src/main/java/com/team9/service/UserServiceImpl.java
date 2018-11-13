package com.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.UserDto;
import com.team9.model.Address;
import com.team9.model.Inspector;
import com.team9.model.Passenger;
import com.team9.model.Role;
import com.team9.model.User;
import com.team9.repository.AddressRepository;
import com.team9.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public User getUser(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public boolean saveUser(User u) {
		if(userRepository.findUserByUsername(u.getUsername()) != null) {
			return false;
		}
		if(u.getRole() == Role.INSPECTOR) {
			Inspector i = (Inspector)u;
			userRepository.save(i);
			return true;
		}
		else if(u.getRole() == Role.PASSANGER) {
			Passenger p = (Passenger)u;
			userRepository.save(p);
			return true;
		}
		else if(u.getRole() == Role.ADMIN) {
			userRepository.save(u);
			return true;
		}
		return false;
	}

	@Override
	public User UserDtoToUser(UserDto udto) {
		
		User u = new User();
		
		u.setName(udto.getName());
		u.setUsername(udto.getUsername());
		u.setPassword(udto.getPassword());
		u.setEmail(udto.getEmail());
		u.setPersonalNo(udto.getPersonalNo());
		Address a = addressRepository.findByStreetAndCityAndZip(udto.getAddress().getStreet(), udto.getAddress().getCity(), udto.getAddress().getZip());
		if(a == null) {
			a = new Address();
			a.setStreet(udto.getAddress().getStreet());
			a.setCity(udto.getAddress().getCity());
			a.setZip(udto.getAddress().getZip());
		}
		u.setAddress(a);
		if(udto.getRole().toUpperCase().equals("ADMIN")) {
			u.setRole(Role.ADMIN);
		}
		else if(udto.getRole().toUpperCase().equals("PASSANGER")) {
			u.setRole(Role.PASSANGER);
		}
		else if(udto.getRole().toUpperCase().equals("INSPECTOR")){
			u.setRole(Role.INSPECTOR);
		}
		
		return u;
	}

	
}
