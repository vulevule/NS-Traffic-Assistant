package com.team9.service;

import java.util.List;

import com.team9.dto.LineDto;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface LineService {

	Line createLine(LineDto l) throws LineAlreadyExistsException;
	
	Line updateLine(LineDto l) throws LineNotFoundException;
	
	boolean deleteLine(Long id) throws LineNotFoundException;
	
	List<Line> getAll();
	
	List<Line> getAllByName(String name);
	
	List<Line> getAllByTrafficType(TrafficType type);
	
	List<Line> getAllByTrafficZone(TrafficZone zone);
	
	List<Line> getAllByStation(Long stationId);
	
	Line getById(Long id);
	
	Line getByNameAndType(String name, TrafficType type);
}
