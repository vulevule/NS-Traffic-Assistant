
package com.team9.service;

import java.util.Collection;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.dto.TicketsAndSizeDto;
import com.team9.exceptions.LineNotFoundException;
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
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public interface TicketService {

	TicketReaderDto buyTicket(TicketDto t, String username) throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException, WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException;
	
	TicketsAndSizeDto myTicket( String username, int page, int size) throws UserNotFoundException;
	
	double getTicketPrice(TicketDto t, String username) throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException;


	java.sql.Date calculateExpirationDate(TimeTicketType type, java.sql.Date date);
	
	String generateSerialNumber(TrafficType trafficType, TimeTicketType timeType, TrafficZone trafficZone,
			UserTicketType ut);
	
	boolean useTicket(String serialNo, String username, Long line_id) throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException,  ZonesDoNotMatchException, LineNotFoundException;
	
	TicketReaderDto checkTicket(String serialNo, String username, Long line_id) throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException,  ZonesDoNotMatchException, LineNotFoundException;
	
	ReportDto getReport(int month, int year, String reportType) throws IllegalArgumentException, WrongReportTypeException;

	TicketsAndSizeDto getAll(int page, int size);

	int getNumberOfTicket(String username) throws UserNotFoundException;

	int getNumberOfAllTickets();
}
