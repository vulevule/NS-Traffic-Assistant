package com.team9.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.dto.ValidationDTO;
import com.team9.exceptions.UserNotFoundException;
import com.team9.model.Address;
import com.team9.model.Authority;
import com.team9.model.Inspector;
import com.team9.model.Passenger;
import com.team9.model.Role;
import com.team9.model.User;
import com.team9.model.UserTicketType;
import com.team9.repository.AddressRepository;
import com.team9.repository.AuthorityRepository;
import com.team9.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
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
			Inspector i = new Inspector(u.getName(), u.getPersonalNo(), u.getUsername(), u.getPassword(), u.getEmail(), u.getRole(), u.getAddress());
			User saveUser = userRepository.save(i);
			Authority a = new Authority("INSPECTOR", saveUser);
			authorityRepository.save(a);			
			return true;
		}
		else if(u.getRole() == Role.PASSENGER) {
			Passenger p = new Passenger(u.getName(), u.getPersonalNo(), u.getUsername(), u.getPassword(), u.getEmail(), u.getRole(), u.getAddress(), false, UserTicketType.REGULAR); 
			User saveUser = userRepository.save(p);
			Authority a = new Authority("PASSENGER", saveUser);
			authorityRepository.save(a);
			return true;
		}
		else if(u.getRole() == Role.ADMIN) {
			User saveUser = userRepository.save(u);
			Authority a = new Authority("ADMIN", saveUser);
			authorityRepository.save(a);
			return true;
		}
		return false;
	}
	
	

	@Override
	public User UserDtoToUser(UserDto udto) {
		
		User u = new User();
		
		u.setName(udto.getName());
		u.setUsername(udto.getUsername());
		u.setPassword(passwordEncoder.encode(udto.getPassword()));
		u.setEmail(udto.getEmail());
		u.setPersonalNo(udto.getPersonalNo());
		Address a = addressRepository.findByStreetAndCityAndZip(udto.getAddress().getStreet(), udto.getAddress().getCity(), udto.getAddress().getZip());
		if(a == null) {
			a = new Address();
			a.setStreet(udto.getAddress().getStreet());
			a.setCity(udto.getAddress().getCity());
			a.setZip(udto.getAddress().getZip());
			addressRepository.save(a);
		}
		u.setAddress(a);

		if(udto.getRole().toUpperCase().equals("ADMIN")) {
			u.setRole(Role.ADMIN);
		}
		else if(udto.getRole().toUpperCase().equals("PASSENGER")) {
			u.setRole(Role.PASSENGER);
			
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

public void sendNotificaitionSync(UserDto user)  {

		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sending email...");
        try {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		System.out.println(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Welcome to NSTrafficAssistant");
		mail.setText("Hello " + user.getName() + ",\n\nThank you for using our application. Please wait for our admins to confirm your user ticket type and enjoy your ride. :)");
		javaMailSender.send(mail);}
        catch(MailException e) {e.printStackTrace();};

		System.out.println("Email sent!");
	}



@Override
public User UpdateDtoToUser(UpdateProfileDto update) {
	User user=userRepository.findUserByUsername(update.getUsername());
	if(user==null) {return null;}
	
	if(user.getEmail()!=update.getEmail() || update.getEmail()!="") {
		user.setEmail(update.getEmail());
	}
	
	if(user.getName()!=update.getName() || update.getName()!="") {
		user.setName(update.getName());
	}
	
	if(user.getPassword()!=update.getPassword() || update.getPassword()!="") {
		user.setPassword(update.getPassword());
	}
	
	
	Address a = addressRepository.findByStreetAndCityAndZip(update.getAddress().getStreet(), update.getAddress().getCity(), update.getAddress().getZip());
	if(a == null) {
		a = new Address();
		a.setStreet(update.getAddress().getStreet());
		a.setCity(update.getAddress().getCity());
		a.setZip(update.getAddress().getZip());
		addressRepository.save(a);
	}
	user.setAddress(a);
	
	return user;
}
@Override
public ArrayList<Passenger> readyToValidate() {
	ArrayList<Passenger> passengers= new ArrayList<Passenger>();
	 List<User> users=userRepository.findAll();
	 for (User user : users) {
		 if (user.getRole().equals(Role.PASSENGER) && user.getImagePath()!=null) {
			
			Passenger pas=(Passenger) user;
			if(pas.getActivate()==false) {
			passengers.add(pas);}
				
			}
		 }
	return passengers;
}

@Override
public Passenger validationProcess(ValidationDTO val) throws UserNotFoundException {
	// TODO Auto-generated method stub
	User user=getUser(val.getUsername());
	if (user==null) {
		throw new UserNotFoundException();
		
	}
	Passenger pas=(Passenger)user;
	
	Date date1 = new Date();
	pas.setExpirationDate(date1);
	
	if(val.getTicketType().toUpperCase().equals("STUDENT")) {
		pas.setActivate(true);
		pas.setUserTicketType(UserTicketType.STUDENT);
		return pas;
		
		
	}
	else if(val.getTicketType().toUpperCase().equals("HANDYCAP")) {
		pas.setActivate(true);
		pas.setUserTicketType(UserTicketType.HANDYCAP);
		return pas;
		
	}
    else if(val.getTicketType().toUpperCase().equals("SENIOR")) {
	
	    pas.setActivate(true);
	    pas.setUserTicketType(UserTicketType.SENIOR);
	    return pas;
		
	}
    else {
	throw new UserNotFoundException();}
}

@Override
public boolean SaveUpdated(User u) {
	if(u==null) {
		return false;
	}
	try {
	userRepository.save(u);
	return true;
}catch(Exception e){
	return false;}
}
}
