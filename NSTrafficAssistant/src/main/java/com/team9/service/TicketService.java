
package com.team9.service;

import java.util.Collection;
import java.util.Set;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketReaderDto;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public interface TicketService {

	boolean buyTicket(Ticket t);
	
	Collection<Ticket> allTicket(String username);
	
	double getTicketPrice(TimeTicketType timeType, TrafficZone trafficZone, TrafficType trafficType);

	Set<TicketReaderDto> getReports(ReportDto report);
	
	
}
