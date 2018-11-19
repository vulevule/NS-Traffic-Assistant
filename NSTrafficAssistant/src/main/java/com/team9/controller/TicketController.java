package com.team9.controller;



import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.model.Ticket;
import com.team9.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value="/myTicket/{username}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Ticket> getMyTicket(@PathVariable String username){
		logger.info(">> get tickets by " + username);
		
		Collection<Ticket> allTicket = ticketService.allTicket(username);
		
		logger.info("<< get tickets by " + username);
		return allTicket;	
	}
	
	@GetMapping(value="/price", consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double>  getTicketPrice(@RequestBody TicketDto t){
		//trazimo cenu za izabrane parametre 
		logger.info(">> get ticket price by: " + t.getTimeType() + "; " + t.getTrafficZone() + "; " + t.getTrafficType());
		double price = ticketService.getTicketPrice(t.getTimeType(), t.getTrafficZone(), t.getTrafficType());
		
		logger.info("<< get ticket price: " + price);
		return new ResponseEntity<Double>(price, HttpStatus.OK);
	}

	@PostMapping(value="/reports", consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<TicketReaderDto>> getReports(@RequestBody ReportDto report){
		logger.info(">> get reports: start date: " + report.getStartDate() + "; end time: " + report.getEndDate());
		Set<TicketReaderDto> tickets = this.ticketService.getReports(report);
		
		logger.info("<< get reports: start date: " + report.getStartDate() + "; end time: " + report.getEndDate());
		return new ResponseEntity<Set<TicketReaderDto>>(tickets, HttpStatus.OK);
		
	}


/*	@PostMapping(value="/buyTicket", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buyTicket(@RequestBody TicketDto t){
		logger.info(">> buy ticket: " + t.getTrafficZone() + "; " + t.getTimeType() + "; " + t.getTrafficType() + "; " + t.getPrice() );
		
	}*/
}

