package com.team9.service;

import java.util.Collection;

import com.team9.dto.StationDTO;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;

public interface StationService {
	Station createStation(StationDTO s) throws StationAlreadyExistsException;
	
	boolean deleteStation(Long id) throws StationNotFoundException;
	
	Station updateStation(StationDTO s) throws StationNotFoundException;
	
	Collection<Station> getAll();
	
	Collection<Station> getAllByType(TrafficType t);
	
	Collection<Station> getAllByLine(Long lineId);
	
	Collection<Station> getByName(String name);
	
	Collection<Station> getByNameContains(String name);
	
	Station getById(Long id);
	
	Station getByNameAndType(String name, TrafficType type);
	
}
