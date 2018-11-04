package com.team9.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tickets")
public class Ticket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String serialNo;
	@Column(nullable = false)
	private Date issueDate;
	@Column(nullable = false)
	private Date expirationDate;
	@Column(nullable = false)
	private UserTicketType userType;
	@Column(nullable = false)
	private TimeTicketType timeType;
	@Column(nullable = false)
	private TrafficZone trafficZone;
	@Column(nullable = false)
	private Boolean active;
	@Column(nullable = false)
	private Double price;
	@ManyToOne(optional=false)
	private Passenger passenger;
	@ManyToOne(optional=false)
	private Inspector inspector;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="tickets")
	private Set<Inspector> checkInspectors;

}
