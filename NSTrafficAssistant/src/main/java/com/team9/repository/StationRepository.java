package com.team9.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.team9.model.Station;
import com.team9.model.TrafficType;

public interface StationRepository extends CrudRepository<Station, Long>{
	
	Collection<Station> findByName(String name);
	
	Station findByNameAndType(String name, TrafficType type);
	
	Optional<Station> findById(Long id);
	
	Collection<Station> findByType(TrafficType type);
}
