package com.team9.model;

import java.util.Set;

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="user")
	private Set<Ticket> tickets;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="inspector_ticket", joinColumns = { @JoinColumn(name="inspector_id")}, inverseJoinColumns = {@JoinColumn(name="ticket_id")})
	private Set<Ticket> checkedTickets;
	
	
	
}
