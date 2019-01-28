package com.team9.controller;

import java.text.ParseException;
import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.TimetableDto;
import com.team9.dto.TimetableItemCreateDto;
import com.team9.dto.TimetableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivateTimetable;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.service.TimetableService;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

	@Autowired
	private TimetableService timetableService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/getItemByTrafficTypeAndZone", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TimetableItemDto>> getItemByLineAndType(@RequestParam("type") String type,
			@RequestParam("zone") String zone) {
		logger.info(">> get item by zone: " + zone + " and type: " + type);
		try {
			Collection<TimetableItemDto> items = this.timetableService.getTimeTableItemByZoneAndType(zone, type);
			logger.info("<< get item by line and type");
			return new ResponseEntity<Collection<TimetableItemDto>>(items, HttpStatus.OK);
		} catch (WrongTrafficZoneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Collection<TimetableItemDto>>(HttpStatus.BAD_REQUEST);

		} catch (WrongTrafficTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Collection<TimetableItemDto>>(HttpStatus.CONFLICT);

		} catch (NotFoundActivateTimetable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Collection<TimetableItemDto>>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(value = "/addTimetable", produces = MediaType.TEXT_PLAIN_VALUE, consumes = "application/json")
	public ResponseEntity<String> addTimetable(@RequestBody TimetableDto newTimetable) {
		logger.info(">> add timetable line mark: " + newTimetable.getTimetables().size());
		try {
			Boolean success = this.timetableService.addTimetable(newTimetable);
			if (success == true) {
				return new ResponseEntity<String>("The timetable has been successfully added!", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("An error occurred, the timetable was not saved!",
						HttpStatus.BAD_REQUEST);
			}
		} catch (LineNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return new ResponseEntity<>("Invalid input data!", HttpStatus.BAD_REQUEST);

		} catch (WrongTrafficTypeException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}

