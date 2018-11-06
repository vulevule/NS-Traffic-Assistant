package com.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.team9.model.Ticket;
import com.team9.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public boolean buyTicket(Ticket t) {
		// 
		return false;
	}

	@Override
	public Page<Ticket> allTicket(Long passenger_id) {
		// return all tickets for one passenger
		return ticketRepository.findByPassenger(passenger_id);
	}

	@Override
	public Page<Ticket> reporst(int period) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
