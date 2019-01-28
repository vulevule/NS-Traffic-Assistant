package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Timetable;
import com.team9.repository.TimeTableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimetableRepositoryIntegrationTest {
	
	@Autowired
	private TimeTableRepository repository;
	
	//testiramo metodu koja nam vraca aktivan red voznje 
	@Test
	public void test_findByActivate(){
		Optional<Timetable> t = this.repository.findByActivate(true);
		Date issue = new java.sql.Date(new GregorianCalendar(2019, Calendar.JANUARY, 03).getTime().getTime());

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		assertTrue(t.isPresent());
		assertTrue(t.get().getId() == 1);
		assertTrue(fmt.format(t.get().getIssueDate()).equals(fmt.format(issue)));
		assertTrue(t.get().isActivate());
		
		
	}
}
