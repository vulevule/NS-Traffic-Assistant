package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.PriceList;
import com.team9.repository.PriceListRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistRepositoryIntegrationTest {
	
	@Autowired
	private PriceListRepository repository;
	
	/*
	 * testiramo metodu koja nam vraca trenutno aktivan cenovnik
	 */
	
	@Test
	public void test_findByActivate(){
		//postoji trenutno aktivan cenovnik, sa id-em = 10;
		
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList pl = new PriceList(new Long(10), issue, null, true);//ovo ocekujemo
		
		Optional<PriceList> found = this.repository.findByActivateTrue();
		
		assertNotNull(found.get());
		assertTrue(found.get().getId() == 10);
		assertTrue(found.get().getIssueDate().equals(pl.getIssueDate()));
		assertTrue(found.get().isActivate());
	}

	
}
