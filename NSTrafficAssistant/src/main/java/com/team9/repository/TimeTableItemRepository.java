package com.team9.repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Line;
import com.team9.model.Timetable;
import com.team9.model.TimetableItem;

public interface TimeTableItemRepository extends JpaRepository<TimetableItem, Long>{
	
	TimetableItem findByStartTime(Time t);

	List<TimetableItem> findByLineAndTimeTable(Line l, Timetable activeTimetable);

}
