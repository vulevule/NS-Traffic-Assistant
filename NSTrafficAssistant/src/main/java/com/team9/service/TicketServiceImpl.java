package com.team9.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.PricelistReaderDto;
import com.team9.dto.ReportDto;
import com.team9.dto.TicketReaderDto;
import com.team9.model.Passenger;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private PriceListService pricelistService;
	

	@Override
	public boolean buyTicket(Ticket t) {
		// 
		return false;
	}

	@Override
	public Collection<Ticket> allTicket(String username) {
		// return all tickets for one passenger
		Passenger passenger = (Passenger)userService.getUser(username);
		return ticketRepository.findByPassenger(passenger);
	}



	@Override
	public double getTicketPrice(TimeTicketType timeType, TrafficZone trafficZone, TrafficType trafficType) {
		// TODO Auto-generated method stub
		PricelistReaderDto pl = pricelistService.getValidPricelist();
		double price = 0;
	/*	Set<PriceItem> priceItems = pl.getItems();
		PriceItem priceItem = null;
		for(PriceItem pi : priceItems){
			if(pi.getTimeType() == timeType && pi.getTrafficType() == trafficType && pi.getZone() == trafficZone){
				priceItem =  pi;
			}
		}
		double price = 0;
		if(priceItem != null){
			price = priceItem.getPrice();
		}*/
		return price;
	}

	@Override
	public Set<TicketReaderDto> getReports(ReportDto report) {
		// TODO Auto-generated method stub
		//convert date and find tickets
		

		Date startTime = convertToDate(report.getStartDate());
		Date endTime = convertToDate(report.getEndDate());
		Set<Ticket> tickets = this.ticketRepository.findByUserTypeAndTimeTypeAndTrafficZoneAndIssueDateBeforeAndExpirationDateAfter(report.getUserType(),report.getTime(), report.getZone(), startTime, endTime);
		
		return convertTicketToDto(tickets);
	}

	private Set<TicketReaderDto> convertTicketToDto(Set<Ticket> tickets) {
		// TODO Auto-generated method stub
		Set<TicketReaderDto> reports = new HashSet<TicketReaderDto>();
		for(Ticket t : tickets){
			TicketReaderDto tdto = new TicketReaderDto(t.getId(),t.getSerialNo(), convertToString(t.getIssueDate()),
					convertToString(t.getExpirationDate()), t.getUserType(), t.getTimeType(), t.getTrafficZone(), t.getActive(),
					t.getTrafficType(), t.getPrice(), t.getPassenger().getUsername(),
					t.getInspector().getUsername());
			reports.add(tdto);
			}
		return reports;
	}

	private String convertToString(Date date) {
		// TODO Auto-generated method stub
		if(date == null){
			return "";
		}
		SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("dd-MM-yyyy");
		String date_to_string = dateformatyyyyMMdd.format(date);
		return date_to_string;
	}

	private Date convertToDate(String string_date) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Date sqlDate = null;
	    try {
			sqlDate = new java.sql.Date(dateFormat.parse(string_date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlDate;
	}
	
	
	
}
