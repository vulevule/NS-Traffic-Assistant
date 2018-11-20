package com.team9.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.TimetableItem;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface LineRepository extends JpaRepository<Line, Long>{
	
	Line findByName(String name);
	
	Collection<Line> getAllByType(TrafficType tt);
	
	Collection<Line> getAllByZone(TrafficZone tz);
	
	Collection<Line> getAllByStations(Station s);
	
	Collection<Line> getAllByTimetableItems(TimetableItem tti);
}
