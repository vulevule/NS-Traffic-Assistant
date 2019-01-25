package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.User;
import com.team9.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserRepositoryIntegrationTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void test_findByUsername_Exist() {
		
		User user=userRepository.findUserByUsername("peraperic");
		assertNotNull(user);
		assertTrue(user.getId()==3);
		assertTrue(user.getName().equals("Pera Peric"));
		assertTrue(user.getPassword().equals("$2a$10$nWigsJopzu.0AL5bO.79meRVJGcMhSOv5Yxh476nDL5ZZcIBotv7G"));
		assertTrue(user.getEmail().equals("pera@gmail.com"));
		
		
		
		
	}
	
	@Test
    public void test_findByUsername_NotExist() {
		User user=userRepository.findUserByUsername("jelena12");
		assertNull(user);
		
		
		
		
		
		
	}
	
	
	@Test
	public void test_findByUsernameAndPassword_Exist() {
		
		User user=userRepository.findUserByUsernameAndPassword("peraperic","$2a$10$nWigsJopzu.0AL5bO.79meRVJGcMhSOv5Yxh476nDL5ZZcIBotv7G");
		assertNotNull(user);
		assertTrue(user.getId()==3);
		assertTrue(user.getName().equals("Pera Peric"));
		assertTrue(user.getPersonalNo().equals("11129956325632"));
		assertTrue(user.getEmail().equals("pera@gmail.com"));
		
		
		
		
	}
	
	
	@Test
	public void test_findByUsernameAndPassword_NotExist() {
		
		User user=userRepository.findUserByUsernameAndPassword("jelena12","ZcIBotv7G");
		assertNull(user);
		
		
		
		
		
	}
	
	
}
