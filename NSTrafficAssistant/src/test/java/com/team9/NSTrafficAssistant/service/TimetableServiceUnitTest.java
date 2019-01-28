package com.team9.NSTrafficAssistant.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.TimetableDto;
import com.team9.dto.TimetableItemCreateDto;
import com.team9.dto.TimetableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivateTimetable;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.repository.LineRepository;
import com.team9.repository.TimeTableRepository;
import com.team9.service.TimetableItemService;
import com.team9.service.TimetableService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimetableServiceUnitTest {
	
	@Autowired
	private TimetableService service;
	
	@MockBean
	private TimeTableRepository timetable_repo_mock;
	
	@MockBean
	private LineRepository line_repo_mock;
	
	@MockBean
	private TimetableItemService timetableItemService_mock;
	
	
	//1. test kada je pogresan tip prevoza
	@Test(expected = WrongTrafficTypeException.class)
	public void test_getTimetableItem_whenWronngTrafficType() throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable{
		Collection<TimetableItemDto> result = this.service.getTimeTableItemByZoneAndType("first", "plain");
	}
	
	
	//2. test kada je pogresna zona
	@Test(expected = WrongTrafficZoneException.class)
	public void test_getTimetableItem_whenWronngTrafficZone() throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable{
		Collection<TimetableItemDto> result = this.service.getTimeTableItemByZoneAndType("fourth", "bus");
	}
	
	//3. dodavanje timetable-a, kada ne postoji linija
	@Test(expected = LineNotFoundException.class)
	public void addTimetable_whenLineNotFound() throws LineNotFoundException, ParseException, WrongTrafficTypeException{
		TimetableItemCreateDto t = new TimetableItemCreateDto("1W", "ZELEZNICKA - BOCKE", "10:30, 14:15, 14:30", "10:30, 14:15, 14:30", "10:30, 14:15, 14:30", "bus");
		List<TimetableItemCreateDto> timetables = new ArrayList<>();
		timetables.add(t);
		TimetableDto t_dto = new TimetableDto(timetables);
		boolean res = this.service.addTimetable(t_dto);
	}
	
}
