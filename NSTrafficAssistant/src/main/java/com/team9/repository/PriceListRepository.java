package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
	
	PriceList  findByActivateTrue();
	
	PriceList findByIdAndActivate(Long id, Boolean activate);

}
