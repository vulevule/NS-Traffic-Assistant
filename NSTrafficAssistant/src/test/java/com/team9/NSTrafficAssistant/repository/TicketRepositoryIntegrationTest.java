package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

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
		assertTrue(found_1.get().getId() == 8);
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
	

}
