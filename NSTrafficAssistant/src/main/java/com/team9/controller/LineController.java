package com.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.LineDto;
import com.team9.exceptions.StationNotFoundException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.service.LineService;

@RestController
public class LineController {
	
	@Autowired
	private LineService lineService;
	
	@RequestMapping(
			value = "/line/create", 
			method=RequestMethod.POST, 
			consumes="application/json")
	public ResponseEntity<String> createLine(@RequestBody LineDto lineDto){
		
		if(lineService.getByName(lineDto.getName()) != null) {
			return new ResponseEntity<String>("Line already exists!" , HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			Line l = lineService.LineDtoToLine(lineDto);
			lineService.createLine(l);
			return new ResponseEntity<String>("Line succesfully created!", HttpStatus.CREATED);
			
		}catch(WrongTrafficTypeException wtte) {
			
			wtte.printStackTrace();
			
			return new ResponseEntity<String>("Wrong traffic type chosen!" , HttpStatus.BAD_REQUEST);
			
		}catch(WrongTrafficZoneException wtze) {
			
			wtze.printStackTrace();
			
			return new ResponseEntity<String>("Wrong traffic zone chosen!" , HttpStatus.BAD_REQUEST);
			
		}catch(StationNotFoundException snfe) {
			
			snfe.printStackTrace();
			
			return new ResponseEntity<String>("The selected station was not found!" , HttpStatus.BAD_REQUEST);
			
		}		
	}

}
