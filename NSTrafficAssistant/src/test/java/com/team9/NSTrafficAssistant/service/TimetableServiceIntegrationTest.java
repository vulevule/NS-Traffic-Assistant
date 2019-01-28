package com.team9.NSTrafficAssistant.service;

import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.TimetableDto;
import com.team9.dto.TimetableItemCreateDto;
import com.team9.dto.TimetableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivateTimetable;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Timetable;
import com.team9.repository.TimeTableRepository;
import com.team9.service.TimetableService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TimetableServiceIntegrationTest {

	@Autowired
	private TimetableService service;

	@Autowired
	private TimeTableRepository repository;

	// 1.getTimetableItemByZoneAndType - kada je pogresna zona

	@Test(expected = WrongTrafficZoneException.class)
	public void test_getTimetableItem_whenWrongZone()
			throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable {
		Collection<TimetableItemDto> res = this.service.getTimeTableItemByZoneAndType("zone", "bus");
	}

	// 2. -kada je pogresan tip prevoza
	@Test(expected = WrongTrafficTypeException.class)
	public void test_getTimetableItem_whenWrongTrafficType()
			throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable {
		Collection<TimetableItemDto> res = this.service.getTimeTableItemByZoneAndType("first", "plain");
	}

	// 3. -kada je sve ok
	@Test
	public void test_getTimetableItem_ok()
			throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable {
		List<TimetableItemDto> res = (List<TimetableItemDto>) this.service.getTimeTableItemByZoneAndType("first",
				"bus");
		// treba da nam vrati jednu liniju
		assertNotNull(res);
		assertTrue(res.size() == 1);
		assertTrue(res.get(0).getLine_mark().equals("1A"));
		assertTrue(res.get(0).getLine_name().equals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA"));
	}

	// dodavanje reda voznje kada linija ne postoji
	@Test(expected = LineNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void addTimetable_lineNotFound() throws LineNotFoundException, ParseException, WrongTrafficTypeException {
		TimetableItemCreateDto t = new TimetableItemCreateDto("1W", "ZELEZNICKA - BOCKE", "10:30, 14:15, 14:30",
				"10:30, 14:15, 14:30", "10:30, 14:15, 14:30", "bus");
		List<TimetableItemCreateDto> timetables = new ArrayList<>();
		timetables.add(t);
		TimetableDto t_dto = new TimetableDto(timetables);
		boolean res = this.service.addTimetable(t_dto);
	}

	// dodavanje reda voznje kada je sve ok
	@Test
	@Transactional
	@Rollback(true)
	public void addTimetable_ok() throws LineNotFoundException, ParseException, WrongTrafficTypeException {
		TimetableItemCreateDto t = new TimetableItemCreateDto("1A", "ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA",
				"10:30, 14:15, 14:30", "10:30, 14:15, 14:30", "10:30, 14:15, 14:30", "bus");
		List<TimetableItemCreateDto> timetables = new ArrayList<>();
		timetables.add(t);
		TimetableDto t_dto = new TimetableDto(timetables);
		boolean res = this.service.addTimetable(t_dto);

		assertTrue(res);
		Optional<Timetable> aTimetable = this.repository.findByActivate(true);
		assertTrue(aTimetable.isPresent());
		assertNotNull(aTimetable.get());
		Timetable at = aTimetable.get();
		Date issueDate = at.getIssueDate();
		Date today = new Date();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		assertTrue(fmt.format(issueDate).equals(fmt.format(today)));
	}

}
