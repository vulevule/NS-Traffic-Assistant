package com.team9.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RestController
public class StationController {
	
	@Autowired
	private StationService stationService;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value="/station/getAllByType/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Station>> getAllByType(@PathVariable TrafficType type){
		logger.info(">> get stations by type " + type);
		
		ResponseEntity<List<Station>> allStations = new ResponseEntity<List<Station>>((List<Station>) stationService.getAllByType(type), HttpStatus.OK);
		
		logger.info("<< get stations by type " + type);
		return allStations;
	}
	
	@GetMapping(value="/station/getAllByLine/{line}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Station>> getAllByLine(@PathVariable Long lineId){
		logger.info(">> get stations by line " + lineId);
		
		ResponseEntity<List<Station>> allStations = new ResponseEntity<List<Station>>((List<Station>) stationService.getAllByType(type), HttpStatus.OK);
		
		logger.info("<< get stations by line " + lineId);
		return allStations;
	}
	
	@GetMapping(value="/station/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Station>> getAll(){
		logger.info(">> get all stations");
		
		ResponseEntity<Collection<Station>> allStations = new ResponseEntity<Collection<Station>>(stationService.getAll(), HttpStatus.OK);
		
		logger.info("<< get all stations");
		return allStations;
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value="/station/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> createStation(@RequestBody Station station){
		logger.info(">> Creating station  " + station.getName());
		
		Station created = stationService.createStation(station);
		
		logger.info("<< Creating station  " + station.getName());
		if (created == null) {
			return new ResponseEntity<>(created, HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value="/station/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> updateStation(@RequestBody Station station){
		logger.info(">> Updating station  " + station.getName());
		
		Station updated = stationService.updateStation(station);
		
		logger.info("<< Updating station  " + station.getName());
		if (updated == null) {
			return new ResponseEntity<>(updated, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(updated, HttpStatus.CREATED);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value="/station/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteStation(@PathVariable Long id){
		logger.info(">> Deleting station  " + id);
		
		boolean retVal = stationService.deleteStation(id);
		
		logger.info("<< Deleting station  " + id);
		if(retVal) {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(retVal, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
