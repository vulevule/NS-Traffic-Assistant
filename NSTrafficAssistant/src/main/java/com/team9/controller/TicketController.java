package com.team9.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketIsNotUseException;
import com.team9.exceptions.TicketIsNotValidException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
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
	@GetMapping(value = "/myTicket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TicketReaderDto>> getMyTicket(HttpServletRequest request, Pageable pageable) {
		/*
		 * korisnika uzimamo iz tokena
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> get tickets by " + username);

		Collection<TicketReaderDto> allTicket;
		try {
			allTicket = ticketService.allTicket(username, pageable);
			logger.info("<< get tickets by " + username);
			return new ResponseEntity<Collection<TicketReaderDto>>(allTicket, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("<< get ticket: not found user");
			return new ResponseEntity<Collection<TicketReaderDto>>(HttpStatus.BAD_REQUEST);
		}

		
	}

	@GetMapping(value = "/price", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getTicketPrice(@RequestBody TicketDto t, HttpServletRequest request) {
		// trazimo cenu za izabrane parametre i treba mu jos proslediti i
		// korisnika da bi znali za koji tip korisnika trazimo kartu
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);

		logger.info(">> get ticket price by: " + t.getTimeType() + "; " + t.getTrafficZone() + "; " + t.getTrafficType()
				+ "; " + username);
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
			return new ResponseEntity<Double>((double) 0, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/buyTicket", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketReaderDto> buyTicket(@RequestBody TicketDto t, HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> buy ticket: " + t.getTrafficZone() + "; " + t.getTimeType() + "; " + t.getTrafficType() + "; "
				+ t.getPrice() + "; " + username);
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

	@PutMapping(value = "/useTicket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> useTicket(@RequestParam("serialNo") String serialNo, HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> use ticket with serial number: " + serialNo + "; user:  " + username);
		try {
			boolean use = this.ticketService.useTicket(serialNo, username);
			if (use == true) {
				logger.info("<< successful use ticket");
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (TicketNotFound e) {
			// TODO Auto-generated catch block
			logger.info(">> use ticket: ticket not found");
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);

		} catch (TicketAlreadyUsedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("use ticket: ticket already used exception");
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		} catch (TicketIsNotValidException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("ticket is not a valid");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@PutMapping(value = "/checkTicket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketReaderDto> checkTicket(@RequestParam("serialNo") String serialNo,
			HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> check ticket with serial number: " + serialNo + "; inspector:  " + username);

		try {
			TicketReaderDto t = this.ticketService.checkTicket(serialNo, username);
			logger.info("<< check ticket");
			return new ResponseEntity<TicketReaderDto>(t, HttpStatus.OK);
		} catch (TicketNotFound e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket not found");
			return new ResponseEntity<TicketReaderDto>(HttpStatus.NOT_FOUND);
		} catch (TicketIsNotUseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket is not use");
			return new ResponseEntity<TicketReaderDto>(HttpStatus.BAD_REQUEST);
		} catch (TicketIsNotValidException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket is not valid");
			return new ResponseEntity<TicketReaderDto>(HttpStatus.CONFLICT);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("<< check ticket : inspector not found");
			return new ResponseEntity<TicketReaderDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/monthReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TicketReaderDto>> getMonthReports(@RequestParam("month") int month,
			@RequestParam("year") int year) {
		logger.info(">> month report: month " + month + "; year " + year);

		try {
			Collection<TicketReaderDto> ticket = this.ticketService.getMonthReport(month, year);
			logger.info("<< month report: " + ticket.size());
			return new ResponseEntity<Collection<TicketReaderDto>>(ticket, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			logger.info("<< month report: illegal argument");
			return new ResponseEntity<Collection<TicketReaderDto>>(HttpStatus.BAD_REQUEST);
		}

	}
}
