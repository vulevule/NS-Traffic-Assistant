
package com.team9.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public interface TicketService {

	TicketReaderDto buyTicket(TicketDto t, String username) throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException, WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException;
	
	Collection<TicketReaderDto> allTicket(Pageable pageable, String username);
	
	double getTicketPrice(TicketDto t, String username) throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException;

	Set<TicketReaderDto> getReports(ReportDto report);
	
	java.sql.Date calculateExpirationDate(TimeTicketType type, java.sql.Date date);
	
	String generateSerialNumber(TrafficType trafficType, TimeTicketType timeType, TrafficZone trafficZone,
			UserTicketType ut);
	
	boolean useTicket(String serialNo, String username) throws TicketNotFound, TicketAlreadyUsedException;
}
