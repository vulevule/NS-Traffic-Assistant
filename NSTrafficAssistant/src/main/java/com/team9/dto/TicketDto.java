package com.team9.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.team9.model.Inspector;
import com.team9.model.Passenger;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public class TicketDto implements Serializable{
	
	private String serialNo;
	private String issueDate;
	private String expirationDate;
	private UserTicketType userType;
	private TimeTicketType timeType;
	private TrafficZone trafficZone;
	private Boolean active;
	private Double price;
	private String  username_passenger;
	private String username_inspector;
	private Set<Inspector> checkInspectors;
}
