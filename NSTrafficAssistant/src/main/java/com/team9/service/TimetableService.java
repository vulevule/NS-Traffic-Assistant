package com.team9.service;

import java.text.ParseException;
import java.util.Collection;

import com.team9.dto.TimetableDto;
import com.team9.dto.TimetableItemCreateDto;
import com.team9.dto.TimetableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivateTimetable;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;

public interface TimetableService {
	
	
	Collection<TimetableItemDto> getTimeTableItemByZoneAndType(String zone, String type) throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable;

	Boolean addTimetable(TimetableDto newTimetable) throws LineNotFoundException, ParseException;

	
	

}
