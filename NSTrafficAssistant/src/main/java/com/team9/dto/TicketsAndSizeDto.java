package com.team9.dto;

import java.io.Serializable;
import java.util.Collection;

public class TicketsAndSizeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<TicketReaderDto> tickets;
	private int numOfTickets;

	public TicketsAndSizeDto() {

	}

	public TicketsAndSizeDto(Collection<TicketReaderDto> tickets, int numOfTickets) {
		this();
		this.tickets = tickets;
		this.numOfTickets = numOfTickets;
	}

	public Collection<TicketReaderDto> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<TicketReaderDto> tickets) {
		this.tickets = tickets;
	}

	public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}

}
