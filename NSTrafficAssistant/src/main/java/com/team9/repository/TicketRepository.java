package com.team9.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.team9.model.Passenger;
import com.team9.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	Page<Ticket> findByPassenger(Passenger id, Pageable pageable);
	

	Optional<Ticket> findBySerialNo(String serialNo);
}

