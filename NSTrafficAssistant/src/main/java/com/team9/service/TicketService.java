package com.team9.service;

import org.springframework.data.domain.Page;

import com.team9.model.Ticket;

public interface TicketService {

	boolean buyTicket(Ticket t);
	
	Page<Ticket> allTicket(Long passenger_id);
	
	Page<Ticket> reporst(int period);
	
}
