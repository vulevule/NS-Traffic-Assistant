package com.team9.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import com.team9.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long>{

	Page<Ticket> findByPassenger(Long id);
}
