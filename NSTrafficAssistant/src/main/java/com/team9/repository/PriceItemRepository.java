package com.team9.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public interface PriceItemRepository extends JpaRepository<PriceItem, Long>{
	
	Set<PriceItem> findByPricelist(PriceList pl);
	
	Optional<PriceItem> findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType tt, TimeTicketType time, TrafficZone zone, PriceList id);
	
}
