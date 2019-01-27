package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Line;
import com.team9.model.Timetable;
import com.team9.model.TimetableItem;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.TimeTableItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimetableItemRepositoryIntegrationTest {
	
	@Autowired
	private TimeTableItemRepository repository;
	
	
	//trazimo po liniji i redu voznje 
	@Test
	public void test_findByLineAndTimetable(){
		Line l = new Line(1L, "1A", "ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", TrafficType.BUS, TrafficZone.FIRST, null, null);
		Timetable t = new Timetable(1L, null, new Date(new java.util.Date().getTime()), null);
		
		List<TimetableItem> items = this.repository.findByLineAndTimeTable(l, t);
		assertNotNull(items);
		assertTrue(items.size() == 14);
	}
	
	//kada za neku liniju ne postoje itemi
	@Test
	public void test_findByLineAndTimetable_whenNotExist(){
		Line l = new Line(2L, "1T", "ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", TrafficType.METRO, TrafficZone.FIRST, null, null);
		Timetable t = new Timetable(1L, null, new Date(new java.util.Date().getTime()), null);
		
		List<TimetableItem> items = this.repository.findByLineAndTimeTable(l, t);
		assertNull(items);
	}

}
