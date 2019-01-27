package com.team9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Timetable;

public interface TimeTableRepository extends JpaRepository<Timetable, Long>{
	Optional<Timetable> findByActivate(boolean active);
}
