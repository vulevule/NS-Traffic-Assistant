package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.AddressDto;
import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.dto.ValidationDTO;
import com.team9.exceptions.UserNotFoundException;
import com.team9.model.Address;
import com.team9.model.Passenger;
import com.team9.model.Role;
import com.team9.model.User;
import com.team9.model.UserTicketType;
import com.team9.repository.AddressRepository;
import com.team9.repository.UserRepository;
import com.team9.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Test
	public void saveUser_Failure() {
		Passenger p = new Passenger("Pera Peric", "123456", "peraperic", "pass", "pera@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==false);
		
		
		
	}
	
	@Test
	public void saveUser_SuccessPassenger() {
		Passenger p = new Passenger("Mira Peric", "1234567", "pericmira", "pass", "mira@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==true);
		
		
		
	}
	
	@Test
	public void saveUser_SuccessAdmin() {
		Passenger p = new Passenger("Mira Peric", "1234567", "pericmira2", "pass", "mira@gmail.com", Role.ADMIN,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==true);
		
		
		
	}
	
	@Test
	public void saveUser_SuccessInspector() {
		Passenger p = new Passenger("Mira Peric", "1234567", "pericmira4", "pass", "mira@gmail.com", Role.INSPECTOR,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==true);
		
		
		
	}
	
	@Test
	public void UserDTOToUser_Passenger() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira3", "pass", "pass","mira@gmail.com","PASSENGER",new AddressDto("Vuka Karadzica 5", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.PASSENGER);
		
		
		
		
	}
	
	@Test
	public void UserDTOToUser_Admin() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira3", "pass", "pass","mira@gmail.com","ADMIN",new AddressDto("Vuka Karadzica 5", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.ADMIN);
		
		
		
		
	}
	
	@Test
	public void UserDTOToUser_Inspector() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira6", "pass", "pass","mira@gmail.com","ADMIN",new AddressDto("Vuka Karadzica 5", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.ADMIN);
		}
	
	
	@Test
	public void UserDTOToUser_NewAddress() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira", "pass", "pass","mira@gmail.com","INSPECTOR",new AddressDto("Vuka Karadzica", "Beograd", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.INSPECTOR);
		
		
		
		
	}
	
	@Test
	public void UpdateDtoToUser_ChangeName() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("peraperic", "Petko Peric", "pera@gmail.com","1111", new AddressDto("Bulevar Vojvode Stepe", "Beograd", 11000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getName().equals(u.getName()));
		
	}
	
	@Test
	public void UpdateDtoToUser_ChangeEmail() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("peraperic", "Petko Peric", "petko@gmail.com","1111", new AddressDto("Bulevar Vojvode Stepe", "Beograd", 11000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getName().equals(u.getName()));
		
	}
	
	@Test
	public void UpdateDtoToUser_ChangePassword() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("peraperic", "Petko Peric", "petko@gmail.com","1112", new AddressDto("Bulevar Vojvode Stepe", "Beograd", 11000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getName().equals(u.getName()));
		
	}
	
	
	@Test
	public void UpdateDtoToUser_ChangeAddress() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera", "Petko Peric", "petko@gmail.com","1112", new AddressDto("Danila Kisa 58", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertNull(addressRepository.findByStreetAndCityAndZip(upd.getAddress().getStreet(), upd.getAddress().getCity(),upd.getAddress().getZip()));
		
	}
	
	@Test
	public void ReadyToValidate() {
		
		
		
		ArrayList<Passenger> users= userService.readyToValidate();
		for (Passenger passenger : users) {
			assertTrue(passenger.getRole()==Role.PASSENGER);
		}
		
	}
	
	@Test
	public void ValidationProcess() throws UserNotFoundException {
		
		
		ValidationDTO val=new ValidationDTO("peraperic","STUDENT");
	    Passenger p= userService.validationProcess(val);
		assertTrue(p.getActivate()==true);
		assertTrue(p.getUserTicketType()==UserTicketType.STUDENT);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void ValidationProcess_Exception() throws UserNotFoundException {
		
		
		ValidationDTO val=new ValidationDTO("pericpera","");
	    Passenger p= userService.validationProcess(val);
		
	}
	/*
	@Test
	public void SaveUpdated (){
		
		
		Passenger p = new Passenger("Petra Peric", "11129956325632", "petraperic", "pass123", "petra@gmail.com", Role.PASSENGER,
				new AddressDto("Vuka Karadzica 5", "Novi Sad", 210000), false, UserTicketType.STUDENT);
		
	boolean value=userService.SaveUpdated(p);
	assertTrue(value);
			
	}*/
	
	

}
