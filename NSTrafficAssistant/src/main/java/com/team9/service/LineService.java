package com.team9.service;

import java.util.Collection;

import com.team9.dto.LineDto;
import com.team9.exceptions.StationNotFoundException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.TimetableItem;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface LineService {

	boolean createLine(Line l);
	
	Line updateLine(Line l);
	
	boolean deleteLine(Line l);
	
	Line getByName(String name);
	
	Collection<Line> getAllByTrafficType(TrafficType tt);
	
	Collection<Line> getAllByTrafficZone(TrafficZone tz);
	
	Collection<Line> getAllByStation(Station s);
	
	Collection<Line> getAllByTimetableItem(TimetableItem tti);
	
	Line LineDtoToLine(LineDto ldto) throws WrongTrafficTypeException, WrongTrafficZoneException, StationNotFoundException;
}
