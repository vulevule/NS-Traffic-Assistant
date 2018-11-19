package com.team9.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.PriceItem;
import com.team9.model.PriceList;

public interface PriceItemRepository extends JpaRepository<PriceItem, Long>{
	
	Set<PriceItem> findByPricelist(PriceList pl);
	
}
