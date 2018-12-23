
package com.team9.service;

import java.util.Collection;
import java.util.Set;

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
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface TicketService {

	TicketReaderDto buyTicket(TicketDto t, String username) throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException, WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException;
	
	Collection<Ticket> allTicket(String username);
	
	double getTicketPrice(TimeTicketType timeType, TrafficZone trafficZone, TrafficType trafficType, String username) throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException;

	Set<TicketReaderDto> getReports(ReportDto report);
	
	
}
