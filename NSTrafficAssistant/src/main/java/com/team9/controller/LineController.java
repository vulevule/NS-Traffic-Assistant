package com.team9.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.LineDto;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.service.LineService;

@RestController
public class LineController {
	
	@Autowired
	private LineService lineService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/line/create", produces = "text/plain", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createLine(@RequestBody LineDto line){
		logger.info(">> Creating line  " + line.getName());
		
		Line created = null;
		String message;
		try {
			created = lineService.createLine(line);
			logger.info("<< Creating line  " + line.getName());
			message = "Line " + line.getName() + " successfully created";
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (LineAlreadyExistsException e) {
			message = line.getType() + " line " + line.getMark() + " already exists!";
			return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
		} catch (StationNotFoundException e) {
			message = e.getMessage();
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping(value="/line/update", produces = "text/plain", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateLine(@RequestBody LineDto line){
		logger.info(">> Updating station  " + line.getName());
		
		Line updated = null;
		String message;
		try {
			updated = lineService.updateLine(line);
			logger.info("<< Updating station  " + line.getName());
			logger.info("\nGLEDAJ VAMO: " + updated.getStations().size() + "\n");
			message = "Line successfully updated!";
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (LineNotFoundException e) {
			message = "Selected line does not exist!";
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} catch (StationNotFoundException e) {
			message = e.getMessage();
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} catch (LineAlreadyExistsException e) {
			message = line.getType() + " line " + line.getMark() + " already exists!";
			return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@DeleteMapping(value="/line/delete/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteStation(@PathVariable Long id){
		logger.info(">> Deleting line  " + id);

		String message;
		try {
			lineService.deleteLine(id);
			logger.info("<< Deleting line  " + id);
			message = "Line successfully deleted!";
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (LineNotFoundException e) {
			message = "Line does not exist!!";
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/line/getAllByType/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineDto>> getAllByType(@PathVariable TrafficType type){
		logger.info(">> get lines by type " + type);
		
		List<Line> lines = lineService.getAllByTrafficType(type);
		
		List<LineDto> retVal = convertLinesToDTO(lines);
		
		logger.info("<< get lines by type " + type);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/line/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineDto>> getAll(){
		logger.info(">> get all lines");
		
		List<Line> lines = lineService.getAll();
		
		List<LineDto> retVal = convertLinesToDTO(lines);
		
		logger.info("<< get all lines");
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/line/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LineDto> getById(@PathVariable Long id){
		logger.info(">> get line by id " + id);
		
		Line found = lineService.getById(id);
		
		logger.info("<< get line by id " + id);
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new LineDto(found), HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/line/getByMarkAndType/{mark}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LineDto> getByNameAndType(@PathVariable("mark") String mark, @PathVariable("type") TrafficType type) throws UnsupportedEncodingException{
		mark = URLDecoder.decode(mark, "UTF-8" );
		
		logger.info(">> get " + type + " line by mark " + mark);

		Line found = lineService.getByMarkAndType(mark, type);
		
		logger.info("<< get " + type + " line by mark " + mark);
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new LineDto(found), HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/line/getAllByStation/{stationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LineDto>> getAllByLine(@PathVariable Long stationId){
		logger.info(">> get stations by station " + stationId);
		
		List<Line> lines = lineService.getAllByStation(stationId);
		
		List<LineDto> retVal = convertLinesToDTO(lines);

		logger.info("<< get stations by station " + stationId);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	private List<LineDto> convertLinesToDTO(List<Line> lines) {
		List<LineDto> retVal = new ArrayList<LineDto>();
		for(Line l : lines) {
			LineDto newDTO = new LineDto(l);
			retVal.add(newDTO);
		}		
		return retVal;
	}

	
	
	
}
