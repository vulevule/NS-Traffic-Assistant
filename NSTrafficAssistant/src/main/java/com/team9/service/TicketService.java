package com.team9.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.team9.model.Ticket;

public interface TicketService {

	boolean buyTicket(Ticket t);
	
	Collection<Ticket> allTicket(String username);
	
	Page<Ticket> reports(int period);
	
}
