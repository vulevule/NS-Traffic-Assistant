
package com.team9.service;

import java.util.Collection;

import com.team9.model.Ticket;

public interface TicketService {

	boolean buyTicket(Ticket t);
	
	Collection<Ticket> allTicket(String username);
	
	Collection<Ticket> reports(int period);
	
}
