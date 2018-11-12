package com.team9.service;

import java.util.Collection;

import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.TrafficType;

public interface StationService {
	Station createStation(Station s);
	
	boolean deleteStation(Station s);
	
	Station updateStation(Station s);
	
	Collection<Station> getAllByType(TrafficType t);
	
	Collection<Station> getAllByLine(Line l);
	
	Collection<Station> getByName(String name);
	
	Station getById(Long id);
	
}
