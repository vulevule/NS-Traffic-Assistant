package com.team9.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Ticket> tickets;
	
	@Column
	private UserTicketType userTicketType;

	public Passenger() {
		// TODO Auto-generated constructor stub
	}

	public Passenger(Long id, String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate, Set<Ticket> tickets, UserTicketType userTicketType) {
		super(id, name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.tickets = tickets;
		this.userTicketType = userTicketType;
	}

	public Passenger(String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate, Set<Ticket> tickets, UserTicketType userTicketType) {
		super(name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.tickets = tickets;
		this.userTicketType = userTicketType;
	}

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
