package com.team9.service;

import java.util.List;

import com.team9.dto.StationDTO;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;

public interface StationService {
	Station createStation(StationDTO s) throws StationAlreadyExistsException;
	
	boolean deleteStation(Long id) throws StationNotFoundException;
	
	Station updateStation(StationDTO s) throws StationNotFoundException, StationAlreadyExistsException;
	
	List<Station> getAll();
	
	List<Station> getAllByType(TrafficType t);
	
	List<Station> getAllByLine(Long lineId) throws LineNotFoundException;
	
	List<Station> getByName(String name);
	
	List<Station> getByNameContains(String name);
	
	Station getById(Long id);
	
	Station getByNameAndType(String name, TrafficType type);
	
}
