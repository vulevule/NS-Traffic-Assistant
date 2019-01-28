package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Address;
import com.team9.model.Passenger;
import com.team9.model.Role;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.UserTicketType;
import com.team9.repository.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketRepositoryIntegrationTest {
	
	@Autowired
	private TicketRepository repository;
	
	
	/*
	 * 1. testiramo metodu findByTicketBySerialNo
	 */
	//1.a kada postoji karta sa ttrazenim serijskim brojem
	@Test
	public void test_findBySerialNo_whenTicketExists(){
		Optional<Ticket> found_1 = this.repository.findBySerialNo("BMFS12121212000");
		assertNotNull(found_1.get());
		assertTrue(found_1.get().getId() == 1);
		assertTrue(found_1.get().getSerialNo().equals("BMFS12121212000"));
		assertTrue(found_1.get().getUserType() == UserTicketType.STUDENT);
		assertTrue(found_1.get().getTimeType() == TimeTicketType.MONTH);
		assertTrue(found_1.get().getTrafficType() == TrafficType.BUS);
		assertTrue(found_1.get().getPrice() == 900);
	}
	
	//1.b kada ne postoji karta sa trazenim serijskim brojem 
	@Test
	public void test_findBySerialNo_whenTicektNotExist(){
		Optional<Ticket> found_1 = this.repository.findBySerialNo("BMFS12121212005");
		assertFalse(found_1.isPresent());
	}
	
	//2. getAll
	@Test
	public void test_getAllTicket(){
		List<Ticket> tickets = (List<Ticket>) this.repository.findAll();
		assertTrue(tickets.size()  == 6);
	}
	
	//3. getMyTicket - kada user postoji
	@Test
	public void test_myTicket(){
		Passenger p = new Passenger(3L, "Pera", "12212121212", "peraperic", "1111", "pera@gmail.com", Role.PASSENGER, new Address(1L, "Temerinska 3", "Novi Sad", 21000), true, UserTicketType.STUDENT);
		List<Ticket> tickets = (List<Ticket>) this.repository.findByPassenger(p);
		assertTrue(tickets.size() == 1);
	}
	
	//4. getMyTicket - kada user ne postoji
	@Test
	public void test_myTicket_whenUserNotFound(){
		Passenger p = new Passenger(10L, "Pera", "12212121212", "perapetrovic", "2222", "pera@gmail.com", Role.PASSENGER, new Address(1L, "Temerinska 3", "Novi Sad", 21000), true, UserTicketType.STUDENT);
		List<Ticket> tickets = (List<Ticket>) this.repository.findByPassenger(p);
		assertTrue(tickets.size() == 0);
	}

}
