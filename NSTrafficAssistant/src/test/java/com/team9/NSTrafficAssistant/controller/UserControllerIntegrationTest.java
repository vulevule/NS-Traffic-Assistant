package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.apache.catalina.webresources.FileResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.team9.dto.AddressDto;
import com.team9.dto.EditDto;
import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.dto.ValidationDTO;
import com.team9.model.Address;
import com.team9.model.Passenger;
import com.team9.model.User;
import com.team9.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestRestTemplate restTemplate;

	private String token_passenger_active;

	private String token_passenger_no_active;

	private String token_admin;

	private String token_inspector;
	
	
	
	@Before
	public void login() {
		ResponseEntity<LoginUserDto> result = restTemplate.postForEntity("/user/login",
				new LoginDto("laralukic", "7777"), LoginUserDto.class);
		token_admin = result.getBody().getToken();

		ResponseEntity<LoginUserDto> result_passenger = restTemplate.postForEntity("/user/login",
				new LoginDto("peraperic", "1111"), LoginUserDto.class);
		token_passenger_active = result_passenger.getBody().getToken();

		ResponseEntity<LoginUserDto> result_p = restTemplate.postForEntity("/user/login",
				new LoginDto("mikaperic", "5555"), LoginUserDto.class);

		ResponseEntity<LoginUserDto> result_inspector = restTemplate.postForEntity("/user/login",
				new LoginDto("lenalukic", "6666"), LoginUserDto.class);
		token_inspector = result_inspector.getBody().getToken();
		
		
		
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_loginPassenger() {
		
		ResponseEntity<LoginUserDto> result=restTemplate.postForEntity("/user/login",
				new LoginDto("peraperic", "1111"), LoginUserDto.class);
		assertTrue(result.getBody().getRole().equalsIgnoreCase("PASSENGER"));
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		
		
		
	}
	
	/*Test radi kada se samostalno pokrene,
	@Test
	@Transactional
	@Rollback(true)
	public void test_loginAdmin() {
		
		ResponseEntity<LoginUserDto> result=restTemplate.postForEntity("/user/login",
				new LoginDto("laralukic", "7777"), LoginUserDto.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().getRole().equalsIgnoreCase("ADMIN"));
		
		
	}*/
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_loginInspector() {
		
		ResponseEntity<LoginUserDto> result=restTemplate.postForEntity("/user/login",
				new LoginDto("lenalukic", "6666"), LoginUserDto.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().getRole().equalsIgnoreCase("INSPECTOR"));
		
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_loginWrongData() {
		
		ResponseEntity<LoginUserDto> result=restTemplate.postForEntity("/user/login",
				new LoginDto("lenalukic123", "66669"), LoginUserDto.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		
		
		
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_register() {
		UserDto user=new UserDto("Lara Lakic", "1212996156258","laralakic","lara12", "laralakic@gmail.com",
				"PASSENGER");
		AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		user.setAddress(address);
		ResponseEntity<String> result=restTemplate.postForEntity("/user/create",
				user, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.CREATED);
		
		
		
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_registerAlreadyExist() {
		UserDto user=new UserDto("Lara Lakic", "1212996156258","laralakic","lara12", "laralakic@gmail.com",
				"PASSENGER");
		//AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		//user.setAddress(address);
		ResponseEntity<String> result=restTemplate.postForEntity("/user/create",
				user, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
		
		
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_registerBad() {
		UserDto user=new UserDto("Lara Lucic", "1212996156258","laralucic","lara123", "lara15@gmail.com",
				"PASSENGER");
		//AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		//user.setAddress(address);
		ResponseEntity<String> result=restTemplate.postForEntity("/user/create",
				user, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		
		
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_updateProfilePassenger() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		
		AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		UpdateProfileDto dto=new UpdateProfileDto("peraperic","Pera Peric", "peraPeric1@gmail.com", "1111", address);
		HttpEntity<UpdateProfileDto> entity =new HttpEntity<UpdateProfileDto>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileUpdate", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void test_updateProfileAdmin() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		
		AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		UpdateProfileDto dto=new UpdateProfileDto("laralukic","Lara Lukic", "laralara@gmail.com", "7777", address);
		HttpEntity<UpdateProfileDto> entity =new HttpEntity<UpdateProfileDto>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileUpdate", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void test_updateProfileInspector() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		
		AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		UpdateProfileDto dto=new UpdateProfileDto("lenalukic","Lena Lukic", "lenaLena@gmail.com", "6666", address);
		HttpEntity<UpdateProfileDto> entity =new HttpEntity<UpdateProfileDto>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileUpdate", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_updateProfileForbidben() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		
		AddressDto address=new AddressDto("Takovska 18", "Beograd", 11000);
		UpdateProfileDto dto=new UpdateProfileDto("lena","Lena Lukic", "lenaLena@gmail.com", "6666", address);
		HttpEntity<UpdateProfileDto> entity =new HttpEntity<UpdateProfileDto>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileUpdate", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_validateProfile() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		
		
		ValidationDTO dto=new ValidationDTO("mikaPeric", "STUDENT");
		HttpEntity<ValidationDTO> entity =new HttpEntity<ValidationDTO>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileValidated", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_validateProfile1() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		
		
		ValidationDTO dto=new ValidationDTO("mikaPeric", "STUDENT");
		HttpEntity<ValidationDTO> entity =new HttpEntity<ValidationDTO>(dto,headers);
		ResponseEntity<String> result=restTemplate.exchange("/user/profileValidated", HttpMethod.PUT, entity,String.class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_NotValidated() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		
		
		
		HttpEntity<Object> entity =new HttpEntity<Object>(headers);
		ResponseEntity<Passenger[]> result=restTemplate.exchange("/user/notValidated", HttpMethod.GET, entity,Passenger[].class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_getUser() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		
		
		
		HttpEntity<Object> entity =new HttpEntity<Object>(headers);
		ResponseEntity<EditDto> result=restTemplate.exchange("/user/getUser", HttpMethod.GET, entity,EditDto.class);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	
	
	
	
	

}
