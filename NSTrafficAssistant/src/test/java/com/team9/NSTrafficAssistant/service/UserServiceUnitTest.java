package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.team9.service.AddressService;
import com.team9.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {
	
	@Autowired
    private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
//	@MockBean
//	private JavaMailSender javaMailSender;
//	
	@MockBean
	private AddressService addressService;
	
	
	@Before
	public void setUp() {
		
		
		Passenger p = new Passenger("Pera Peric", "123456", "pericpera", "pass", "pera@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		Mockito.when(this.userService.getUser("pericpera")).thenReturn(p);
		
		
		Address a=new Address(1L, "Vuka Karadzica", "Novi Sad", 21000);
		Mockito.when(this.addressService.findByStreetAndCityAndZip(a.getStreet(),a.getCity(),a.getZip())).thenReturn(a);
		
		
		
	}
	
	@Test
	public void saveUser_Failure() {
		Passenger p = new Passenger("Pera Peric", "123456", "pericpera", "pass", "pera@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==false);
		
		
		
	}
	
	
	@Test
	public void saveUser_Success() {
		Passenger p = new Passenger("Mira Peric", "1234567", "pericmira", "pass", "mira@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
		boolean value=userService.saveUser(p);
		assertTrue(value==true);
		
		
		
	}
	
	@Test
	public void UserDTOToUser_Passenger() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira", "pass", "pass","mira@gmail.com","Passenger",new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.PASSENGER);
		
		
		
		
	}
	
	
	@Test
	public void UserDTOToUser_Admin() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira", "pass", "pass","mira@gmail.com","Admin",new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.ADMIN);
		
		
		
		
	}
	
	
	@Test
	public void UserDTOToUser_Inspector() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira", "pass", "pass","mira@gmail.com","Inspector",new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.INSPECTOR);
		
		
		
		
	}
	
	
	@Test
	public void UserDTOToUser_NewAddress() {
		
		UserDto u=new UserDto("Mira Peric", "1234567", "pericmira", "pass", "pass","mira@gmail.com","Inspector",new AddressDto("Vuka Karadzica", "Beograd", 21000));
		User us=userService.UserDtoToUser(u);
		assertTrue(us.getRole()==Role.INSPECTOR);
		
		
		
		
	}
	
//	
//	@Test
//	public void sendNotification() throws MailException, InterruptedException {
//		UserDto u=new UserDto("Jelena Pejicic", "1234567", "pejicicjelena", "pass", "pass","jelenapejicic9@gmail.com","Passenger",new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
//		this.userService.sendNotificaitionSync(u);
//		
//		
//		
//		
//	}
//	
	
	
	
	
	@Test
	public void UpdateDtoToUser_ChangeName() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera", "Petar Peric", "pera@gmail.com","pass", new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getName().equals(u.getName()));
		
	}
	
	
	
	
	@Test
	public void UpdateDtoToUser_ChangePassword() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera", "Pera Peric", "pera@gmail.com","pass12", new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getPassword().equals(u.getPassword()));
		
	}
	
	
	@Test
	public void UpdateDtoToUser_ChangeEmail() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera", "Pera Peric", "pera123@gmail.com","pass", new AddressDto("Vuka Karadzica", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getUsername().equals(u.getUsername()));
		
	}
	
	
	@Test
	public void UpdateDtoToUser_ChangeAddress() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera", "Pera Peric", "pera@gmail.com","pass", new AddressDto("Danila Kisa", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertTrue(upd.getName().equals(u.getName()));
		
	}
	
	
	
	@Test
	public void UpdateDtoToUser_UserNotFound() {
		
		
		UpdateProfileDto upd=new UpdateProfileDto("pericpera123", "Pera Peric", "pera@gmail.com","pass", new AddressDto("Danila Kisa", "Novi Sad", 21000));
		User u= userService.UpdateDtoToUser(upd);
		assertNull(u);
		
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
		
		
		ValidationDTO val=new ValidationDTO("pericpera","Student");
	    Passenger p= userService.validationProcess(val);
		assertTrue(p.getActivate()==true);
		assertTrue(p.getUserTicketType()==UserTicketType.STUDENT);
	}
	
	
	@Test(expected = UserNotFoundException.class)
	public void ValidationProcess_Exception() throws UserNotFoundException {
		
		
		ValidationDTO val=new ValidationDTO("pericpera","");
	    Passenger p= userService.validationProcess(val);
		
	}
	
	
	@Test(expected = UserNotFoundException.class)
	public void ValidationProcess_Exception2() throws UserNotFoundException {
		
		
		ValidationDTO val=new ValidationDTO("pericpera123","Student");
	    Passenger p= userService.validationProcess(val);
		
	}
	
	@Test
	public void SaveUpdated (){
		
		
		Passenger p = new Passenger("Mika Peric", "12345678", "pericmika", "pass123", "pericmika@gmail.com", Role.PASSENGER,
				new Address(1L, "Boska Buhe", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		
	boolean value=userService.SaveUpdated(p);
	assertTrue(value);
			
	}
	
	@Test
	public void SaveUpdated_2 (){
		
		
		Passenger p = null;
		
	boolean value=userService.SaveUpdated(p);
	assertFalse(value);
			
	}
	
	
}
