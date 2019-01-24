package com.team9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.StationLine;

public interface StationLineRepository extends JpaRepository<StationLine, Long>{
	List<StationLine> findByStation(Station station);
	
	List<StationLine> findByLine(Line line);
	
}
