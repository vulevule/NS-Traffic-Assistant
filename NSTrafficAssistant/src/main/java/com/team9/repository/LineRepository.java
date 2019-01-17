package com.team9.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface LineRepository extends JpaRepository<Line, Long>{
		
	List<Line> findByName(String name);
	
	List<Line> findByType(TrafficType type);
	
	List<Line> findByZone(TrafficZone zone);
	
	Line findByNameAndType(String name, TrafficType type);
	
	Optional<Line> findById(Long id);
}
