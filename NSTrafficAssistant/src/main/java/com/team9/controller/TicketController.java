package com.team9.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketIsNotUseException;
import com.team9.exceptions.TicketIsNotValidException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongReportTypeException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.ZonesDoNotMatchException;
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

	
	@GetMapping(value="/all", params={"page", "size"}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TicketReaderDto>> getAll(@RequestParam("page")int page, @RequestParam("size") int size){
		Collection<TicketReaderDto> result = this.ticketService.getAll(page, size);
		return new ResponseEntity<Collection<TicketReaderDto>>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/size", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumberOfTicket(HttpServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		int num;
		try {
			num = this.ticketService.getNumberOfTicket(username);
			return new ResponseEntity<Integer>(num, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * TREBA ZAMENITI DA VRACA PAGE OBJEKAT
	 */
	@GetMapping(value = "/myTicket", produces = MediaType.APPLICATION_JSON_VALUE, params={"page", "size"})
	public ResponseEntity<Collection<TicketReaderDto>> getMyTicket(@RequestParam("page") int page, @RequestParam("size") int size,HttpServletRequest request) {
		/*
		 * korisnika uzimamo iz tokena
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> get tickets by " + username);

		
		
		Collection<TicketReaderDto> allTicket;
		try {
			allTicket = ticketService.allTicket(username, page, size);
			logger.info("<< get tickets by " + username);
			return new ResponseEntity<Collection<TicketReaderDto>>(allTicket, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("<< get ticket: not found user");
			return new ResponseEntity<Collection<TicketReaderDto>>(HttpStatus.BAD_REQUEST);
		}

		
	}

	@GetMapping(value = "/price", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getTicketPrice(@RequestParam("type") String type, @RequestParam("zone") String zone, @RequestParam("time") String time, HttpServletRequest request) {
		// trazimo cenu za izabrane parametre i treba mu jos proslediti i
		// korisnika da bi znali za koji tip korisnika trazimo kartu
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		
		logger.info(zone + " " + time + " " + type + " " + username);
		
		TicketDto t = new TicketDto(time, zone, type, 0);

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
	public ResponseEntity<Object> buyTicket(@RequestBody TicketDto t, HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		
		logger.info(">> buy ticket: " + t.getTrafficZone() + "; " + t.getTimeType() + "; " + t.getTrafficType() + "; "
				+ t.getPrice() + "; " + username);
		try {
			TicketReaderDto ticket = this.ticketService.buyTicket(t, username);
			logger.info("ticket is bought");
			return new ResponseEntity<Object>(ticket, HttpStatus.CREATED);
		} catch (WrongTrafficTypeException | UserNotFoundException | WrongTrafficZoneException
				| WrongTicketTimeException | PriceItemNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("bad request, ticket is not bought");
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotFoundActivePricelistException e) {
			// TODO Auto-generated catch block
			logger.info("pricelist does not exist");
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping(value = "/useTicket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> useTicket(@RequestParam("serialNo") String serialNo, @RequestParam("zone") String zone,  HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> use ticket with serial number: " + serialNo + "; user:  " + username + "; zone: " + zone);
		try {
			boolean use = this.ticketService.useTicket(serialNo, username, zone);
			if (use == true) {
				logger.info("<< successful use ticket");
				return new ResponseEntity<String>("The ticket was successfully used", HttpStatus.OK);
			}
		} catch (TicketNotFound e) {
			// TODO Auto-generated catch block
			logger.info(">> use ticket: ticket not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		} catch (TicketAlreadyUsedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("use ticket: ticket already used exception");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (TicketIsNotValidException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("ticket is not a valid");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (WrongTrafficZoneException e) {
			logger.info("<< use ticket: invalid zone ");
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ZonesDoNotMatchException e) {
			// TODO Auto-generated catch block
			logger.info("<< use ticket: zone do not match");
			//e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return null;
	}

	@PutMapping(value = "/checkTicket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkTicket(@RequestParam("serialNo") String serialNo, @RequestParam("zone") String zone,
			HttpServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("X-Auth-Token");
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		logger.info(">> check ticket with serial number: " + serialNo + "; zone: " + zone + "; inspector:  " + username);

		try {
			TicketReaderDto t = this.ticketService.checkTicket(serialNo, username, zone); 
			logger.info("<< check ticket");
			return new ResponseEntity<String>("The ticket was successfully checked", HttpStatus.OK);
		} catch (TicketNotFound e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket not found");
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (TicketIsNotUseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket is not use");
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (TicketIsNotValidException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info("<< check ticket: ticket is not valid");
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("<< check ticket : inspector not found");
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (WrongTrafficZoneException e) {
			logger.info("<< check ticket: invalid zone ");
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ZonesDoNotMatchException e) {
			// TODO Auto-generated catch block
			logger.info("<< check ticket: zone do not match");
			//e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMonthReports(@RequestParam("month") int month,
			@RequestParam("year") int year, @RequestParam("type")String reportType) {
		logger.info(">> month report: month " + month + "; year " + year + "; report type: " + reportType);

		try {
			ReportDto report = this.ticketService.getReport(month, year, reportType);
			logger.info("<< month report: " );
			return new ResponseEntity<Object>(report, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			logger.info("<< month report: illegal argument");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (WrongReportTypeException e) {
			logger.info("<< report: invalid type of report");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
}
