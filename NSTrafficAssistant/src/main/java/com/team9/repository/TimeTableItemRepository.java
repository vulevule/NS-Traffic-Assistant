package com.team9.repository;

import java.sql.Time;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.TimetableItem;

public interface TimeTableItemRepository extends JpaRepository<TimetableItem, Long>{
	
	TimetableItem findByStartTime(Time t);

}
