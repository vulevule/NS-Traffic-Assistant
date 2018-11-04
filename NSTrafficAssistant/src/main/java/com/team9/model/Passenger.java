package com.team9.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("Passenger")
public class Passenger extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private Boolean activate;
	
	//jedan putnik moze da ima vise karata, karta ima jednog korisnika
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger")
	private Set<Ticket> tickets;
	
	@Column
	private UserTicketType userTicketType;

	public Boolean getActivate() {
		return activate;
	}

	public void setActivate(Boolean activate) {
		this.activate = activate;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public UserTicketType getUserTicketType() {
		return userTicketType;
	}

	public void setUserTicketType(UserTicketType userTicketType) {
		this.userTicketType = userTicketType;
	}
	
	

}
