package com.team9.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("Passenger")
public class Passenger extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private Boolean activate;

	@ManyToOne // onaj koje validirao ulogu
	@JoinColumn(name = "insepctor_id", nullable = true)
	private Inspector inspector;

	// jedan putnik moze da ima vise karata, karta ima jednog korisnika
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Ticket> tickets;

	@Column
	private UserTicketType userTicketType;

	@Column(nullable = true)
	private Date expirationDate;

	public Passenger() {
	}

	public Passenger(String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate, UserTicketType userTicketType) {
		super(name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.userTicketType = userTicketType;
	}
	
	public Passenger(Long id, String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate,UserTicketType userTicketType) {
		super(id, name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.userTicketType = userTicketType;
		
	}


	public Passenger(Long id, String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate, Set<Ticket> tickets, UserTicketType userTicketType, Inspector i) {
		super(id, name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.tickets = tickets;
		this.userTicketType = userTicketType;
		this.inspector = i;
	}

	public Passenger(String name, String personalNo, String username, String password, String email, Role role,
			Address address, Boolean activate, Set<Ticket> tickets, UserTicketType userTicketType, Inspector i) {
		super(name, personalNo, username, password, email, role, address);
		this.activate = activate;
		this.tickets = tickets;
		this.userTicketType = userTicketType;
		this.inspector = i;
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

	public Inspector getInspector() {
		return inspector;
	}

	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
