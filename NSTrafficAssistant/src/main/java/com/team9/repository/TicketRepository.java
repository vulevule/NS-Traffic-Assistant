package com.team9.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.team9.model.Passenger;
import com.team9.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	Collection<Ticket> findByPassenger(Passenger p);
}
