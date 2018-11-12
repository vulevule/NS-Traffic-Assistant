package com.team9.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RestController
public class StationController {
	
	@Autowired
	private StationService stationService;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(
			value="/station/getAllByType/{type}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Station> getAllByType(@PathVariable TrafficType type){
		logger.info(">> get stations by type " + type);
		
		Collection<Station> allStations = stationService.getAllByType(type);
		
		logger.info("<< get stations by type " + type);
		return allStations;
		
	}
	
	
}
