package com.team9.service;

import java.util.Collection;

import com.team9.model.Ticket;

public interface TicketService {

	boolean buyTicket(Ticket t);
	
	Collection<Ticket> allTicket(Long passenger_id);
	
	Collection<Ticket> reporst(int period);
	
}
