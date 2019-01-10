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
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.StationDTO;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RestController
public class StationController {
	
	@Autowired
	private StationService stationService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value="/station/getAllByType/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StationDTO>> getAllByType(@PathVariable TrafficType type){
		logger.info(">> get stations by type " + type);
		
		List<Station> stations = stationService.getAllByType(type);
		
		List<StationDTO> retVal = convertStationsToDTO(stations);
		
		logger.info("<< get stations by type " + type);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/station/getAllByLine/{lineId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StationDTO>> getAllByLine(@PathVariable Long lineId){
		logger.info(">> get stations by line " + lineId);
		
		List<Station> stations = stationService.getAllByLine(lineId);
		
		List<StationDTO> retVal = convertStationsToDTO(stations);

		logger.info("<< get stations by line " + lineId);
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/station/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StationDTO>> getAll(){
		logger.info(">> get all stations");
		
		List<Station> stations = stationService.getAll();
		
		List<StationDTO> retVal = convertStationsToDTO(stations);
		
		logger.info("<< get all stations");
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value="/station/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StationDTO> getById(@PathVariable Long id){
		logger.info(">> get station by id " + id);
		
		Station found = stationService.getById(id);
		
		logger.info("<< get station by id " + id);
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new StationDTO(found), HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/station/getByNameAndType/{name}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StationDTO> getByNameAndType(@PathVariable("name") String name, @PathVariable("type") TrafficType type) throws UnsupportedEncodingException{
		name = URLDecoder.decode(name, "UTF-8" );
		
		logger.info(">> get " + type + " station by name " + name);

		Station found = stationService.getByNameAndType(name, type);
		
		logger.info("<< get " + type + " station by name " + name);
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(new StationDTO(found), HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/station/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StationDTO> createStation(@RequestBody StationDTO station){
		logger.info(">> Creating station  " + station.getName());

		Station created = null;
		try {
			created = stationService.createStation(station);
			logger.info("<< Creating station  " + station.getName());
			return new ResponseEntity<>(new StationDTO(created), HttpStatus.CREATED);
		} catch (StationAlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@PutMapping(value="/station/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StationDTO> updateStation(@RequestBody StationDTO station){
		logger.info(">> Updating station  " + station.getName());

		Station updated = null;
		try {
			updated = stationService.updateStation(station);
			logger.info("<< Updating station  " + station.getName());
			return new ResponseEntity<>(new StationDTO(updated), HttpStatus.CREATED);
		} catch (StationNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/station/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteStation(@PathVariable Long id){
		logger.info(">> Deleting station  " + id);

		try {
			stationService.deleteStation(id);
			logger.info("<< Deleting station  " + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (StationNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private List<StationDTO> convertStationsToDTO(List<Station> stations) {
		List<StationDTO> retVal = new ArrayList<StationDTO>();
		for(Station s : stations) {
			StationDTO newDTO = new StationDTO(s);
			retVal.add(newDTO);
		}
		
		return retVal;
	}
}
