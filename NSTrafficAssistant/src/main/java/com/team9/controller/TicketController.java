package com.team9.controller;



import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Ticket;
import com.team9.security.TokenUtils;
import com.team9.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TokenUtils tokenUtils;
	
	
	/*
	 * TREBA ZAMENITI DA VRACA PAGE OBJEKAT
	 */
	@GetMapping(value="/myTicket", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TicketReaderDto>> getMyTicket(HttpServletRequest request, 
			Pageable pageable){
		/*
		 * korisnika uzimamo iz tokena 
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> get tickets by " + username);

		
		Collection<TicketReaderDto> allTicket = ticketService.allTicket(pageable , username);
		
		logger.info("<< get tickets by " + username);
		return new ResponseEntity<Collection<TicketReaderDto>>(allTicket, HttpStatus.OK);	
	}
	
	@GetMapping(value="/price", consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double>  getTicketPrice(@RequestBody TicketDto t, HttpServletRequest request){
		//trazimo cenu za izabrane parametre i treba mu jos proslediti i korisnika da bi znali za koji tip korisnika trazimo kartu
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		
		logger.info(">> get ticket price by: " + t.getTimeType() + "; " + t.getTrafficZone() + "; " + t.getTrafficType() + "; " + username);
		double price = 0;
		try {
			try {
				price = ticketService.getTicketPrice(t, username);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("user with usrername: " + username + " does not exist");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (NotFoundActivePricelistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("pricelist does not exists");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (WrongTrafficTypeException e) {
				// TODO Auto-generated catch block
				logger.info("wrong trafic type");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} catch (WrongTicketTimeException e) {
				logger.info("wrong ticket time");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} catch (WrongTrafficZoneException e) {
				logger.info("wrong trafic zone");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
							}
			logger.info("<< get ticket price: " + price);
			return new ResponseEntity<Double>(price, HttpStatus.OK);
		} catch (PriceItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("price item does not exist");
			return new ResponseEntity<Double>((double) 0,  HttpStatus.BAD_REQUEST);
		} 
		
		
	}

	@PostMapping(value="/reports", consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<TicketReaderDto>> getReports(@RequestBody ReportDto report){
		logger.info(">> get reports: start date: " + report.getStartDate() + "; end time: " + report.getEndDate());
		Set<TicketReaderDto> tickets = this.ticketService.getReports(report);
		
		logger.info("<< get reports: start date: " + report.getStartDate() + "; end time: " + report.getEndDate());
		return new ResponseEntity<Set<TicketReaderDto>>(tickets, HttpStatus.OK);
		
	}

	@PostMapping(value="/buyTicket", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketReaderDto> buyTicket(@RequestBody TicketDto t, HttpServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> buy ticket: " + t.getTrafficZone() + "; " + t.getTimeType() + "; " + t.getTrafficType() + "; " + t.getPrice()  + "; " + username);
		try {
			TicketReaderDto ticket = this.ticketService.buyTicket(t, username);
			logger.info("ticket is bought");
			return new ResponseEntity<TicketReaderDto>(ticket, HttpStatus.CREATED);
		} catch (WrongTrafficTypeException | UserNotFoundException | WrongTrafficZoneException
				| WrongTicketTimeException | PriceItemNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("bad request, ticket is not bought");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundActivePricelistException e) {
			// TODO Auto-generated catch block
			logger.info("pricelist does not exist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}

