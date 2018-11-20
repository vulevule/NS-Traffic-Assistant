package com.team9.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("Inspector")
public class Inspector extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Ticket> tickets;
	
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="inspector_ticket", joinColumns = { @JoinColumn(name="inspector_id")}, inverseJoinColumns = {@JoinColumn(name="ticket_id")})
	private Set<Ticket> checkedTickets;

	public Inspector() {
		super();
	}


	public Inspector(Long id, String name, String personalNo, String username, String password, String email, Role role,
			Address address, Set<Ticket> tickets, Set<Ticket> checkedTickets) {
		super(id, name, personalNo, username, password, email, role, address);
		this.checkedTickets = checkedTickets;
		this.tickets = tickets;
	}


	public Inspector(String name, String personalNo, String username, String password, String email, Role role,
			Address address) {
		super(name, personalNo, username, password, email, role, address);
	}


	public Set<Ticket> getTickets() {
		return tickets;
	}


	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}


	public Set<Ticket> getCheckedTickets() {
		return checkedTickets;
	}


	public void setCheckedTickets(Set<Ticket> checkedTickets) {
		this.checkedTickets = checkedTickets;
	}
	
	
	
}
