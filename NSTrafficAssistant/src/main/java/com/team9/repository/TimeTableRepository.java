package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Timetable;

public interface TimeTableRepository extends JpaRepository<Timetable, Long>{
	public Timetable findByActivate(boolean active);
}
