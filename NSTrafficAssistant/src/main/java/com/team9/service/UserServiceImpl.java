package com.team9.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.team9.dto.RegisterDto;
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
	
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
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

	@Override
	public User getUser(String username, String password) {
		return userRepository.findUserByUsernameAndPassword(username, password);
	}

public void sendNotificaitionSync(User user) throws MailException, InterruptedException {

		
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");
        
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Welcome to NSTrafficAssistant");
		mail.setText("Hello " + user.getName() + ",\n\nThank you for using our application. Please wait for our admins to confirm your user ticket type and enjoy your ride. :)");
		javaMailSender.send(mail);

		System.out.println("Email sent!");
	}

@Override
public User RegisterDtoToUser(RegisterDto reg) {
	
	User u=new User();
	Address a = addressRepository.findByStreetAndCityAndZip(reg.getAddress().getStreet(), reg.getAddress().getCity(), reg.getAddress().getZip());
	if(a == null) {
		a = new Address();
		a.setStreet(reg.getAddress().getStreet());
		a.setCity(reg.getAddress().getCity());
		a.setZip(reg.getAddress().getZip());
	}
	u.setAddress(a);
	u.setEmail(reg.getEmail());
	u.setUsername(reg.getUserName());
	u.setPassword(reg.getPassword());
	u.setName(reg.getName());
	u.setPersonalNo(reg.getPersonalNo());
	u.setRole(Role.PASSANGER);
	return u;
}
}
