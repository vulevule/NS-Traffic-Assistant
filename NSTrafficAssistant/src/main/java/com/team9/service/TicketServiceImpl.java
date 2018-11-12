package com.team9.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.team9.model.Passenger;
import com.team9.model.Ticket;
import com.team9.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired 
	private UserService userService;

	@Override
	public boolean buyTicket(Ticket t) {
		// 
		return false;
	}

	@Override
	public Collection<Ticket> allTicket(String username) {
		// return all tickets for one passenger
		Passenger passenger = userService.getPassenger(username);
		return ticketRepository.findByPassenger(passenger);
	}

	@Override
	public Collection<Ticket> reports(int period) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
