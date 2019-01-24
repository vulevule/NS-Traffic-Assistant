package com.team9.service;

import java.util.Collection;
import java.util.List;

import com.team9.dto.LineDto;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface LineService {

	Line createLine(LineDto l) throws LineAlreadyExistsException, StationNotFoundException;
	
	Line updateLine(LineDto l) throws LineNotFoundException, StationNotFoundException, LineAlreadyExistsException;
	
	boolean deleteLine(Long id) throws LineNotFoundException;
	
	List<Line> getAll();
	
	List<Line> getAllByName(String name);
	
	List<Line> getAllByTrafficType(TrafficType type);
	
	List<Line> getAllByTrafficZone(TrafficZone zone);
	
	List<Line> getAllByStation(Long stationId);
	
	Line getById(Long id);
	
	Line getByMarkAndType(String name, TrafficType type);
}
