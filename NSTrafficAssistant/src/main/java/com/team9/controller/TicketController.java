package com.team9.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team9.model.Ticket;
import com.team9.service.TicketService;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/ticket/myTicket/{passenger_id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Ticket> getMyTicket(@PathVariable Long passenger_id){
		logger.info(">> get tickets by " + passenger_id);
		
		Collection<Ticket> allTicket = ticketService.allTicket(passenger_id);
		
		logger.info("<< get tickets by " + passenger_id);
		return allTicket;
		
	}
	
}
