package com.team9.controller;

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

import com.team9.dto.TimeTableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.WrongTimeTableTypeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.service.TimeTableService;

@RestController
@RequestMapping("/timetable")
public class TimetableController {
	
	@Autowired
	private TimeTableService  timeTableService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	
	@GetMapping(value="/getItemByTrafficTypeAndZone", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TimeTableItemDto>> getItemByLineAndType(@RequestParam("type") String type, @RequestParam("zone") String zone){
		logger.info(">> get item by zone: " + zone + " and type: " + type);
		try {
			Collection<TimeTableItemDto> items = this.timeTableService.getTimeTableItemByZoneAndType(zone, type);
			logger.info("<< get item by line and type");
			return new ResponseEntity<Collection<TimeTableItemDto>>(items, HttpStatus.OK);
		} catch (WrongTrafficZoneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Collection<TimeTableItemDto>>( HttpStatus.BAD_REQUEST);

		} catch (WrongTrafficTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Collection<TimeTableItemDto>>( HttpStatus.BAD_REQUEST);

		}
		
	}
	
	@PostMapping(value = "/editTimetable", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> editTimetable(@RequestBody TimeTableItemDto items){
		//TREBA PROMENITI
		logger.info(">> edit time table line: " + items.getLine_id());
		try {
			Boolean message = this.timeTableService.editTimeTable(items);
			if(message == true){
				logger.info("<< edit time: success");
				return new ResponseEntity<String>("Successful change of driving order", HttpStatus.OK);
			}else{
				logger.info("<< edit time: failed");
				return new ResponseEntity<>("The change in the drive order failed",HttpStatus.BAD_REQUEST);
			}
		} catch (WrongTimeTableTypeException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			logger.info("<< edit time: wrong time table type");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (LineNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("<< edit time: not found line");
		//	e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	
	

}
