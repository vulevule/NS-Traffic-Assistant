package com.team9.service;

import java.sql.Time;
import java.util.List;

import com.team9.model.Line;
import com.team9.model.Timetable;
import com.team9.model.TimetableItem;

public interface TimetableItemService {

	List<TimetableItem> addItems(Line foundLine, List<Time> workdayTimes, List<Time> saturdayTimes, List<Time> sundayTimes,
			Timetable saveTimetable);
	
	

}
