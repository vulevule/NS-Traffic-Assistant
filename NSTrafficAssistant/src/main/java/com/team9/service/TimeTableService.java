package com.team9.service;

import java.util.Collection;

import com.team9.dto.TimeTableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.WrongTimeTableTypeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;

public interface TimeTableService {
	
	Collection<TimeTableItemDto> getTimeTableItemByZoneAndType(String zone, String type) throws WrongTrafficZoneException, WrongTrafficTypeException;

	Boolean editTimeTable(TimeTableItemDto items) throws WrongTimeTableTypeException, LineNotFoundException;
	
	

}
