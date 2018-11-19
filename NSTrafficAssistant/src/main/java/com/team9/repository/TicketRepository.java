package com.team9.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.team9.model.Passenger;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	Collection<Ticket> findByPassenger(Passenger id);
	
	Set<Ticket> findByUserTypeAndTimeTypeAndTrafficZoneAndIssueDateBeforeAndExpirationDateAfter(UserTicketType userType, TimeTicketType time, TrafficZone zone, Date issueDate, Date expirationDate);
}

