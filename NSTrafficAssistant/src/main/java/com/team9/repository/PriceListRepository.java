package com.team9.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
	
	Optional<PriceList>  findByActivateTrue();
	
	PriceList findByIdAndActivate(Long id, Boolean activate);

}
