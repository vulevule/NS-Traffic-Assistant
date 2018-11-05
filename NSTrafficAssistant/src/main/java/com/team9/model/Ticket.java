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
@Table(name="ticket")
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
	
	@ManyToOne
	private Inspector inspector;
	
	@ManyToMany( mappedBy="tickets")
	private Set<Inspector> checkInspectors;
	
	public Ticket(){}
	
	public Ticket(Long id, String serialNo, Date issueDate, Date expirationDate, UserTicketType userType,
			TimeTicketType timeType, TrafficZone trafficZone, Boolean active, Double price, Passenger passenger,
			Inspector inspector, Set<Inspector> checkInspectors) {
		this();
		this.id = id;
		this.serialNo = serialNo;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.userType = userType;
		this.timeType = timeType;
		this.trafficZone = trafficZone;
		this.active = active;
		this.price = price;
		this.passenger = passenger;
		this.inspector = inspector;
		this.checkInspectors = checkInspectors;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public UserTicketType getUserType() {
		return userType;
	}
	public void setUserType(UserTicketType userType) {
		this.userType = userType;
	}
	public TimeTicketType getTimeType() {
		return timeType;
	}
	public void setTimeType(TimeTicketType timeType) {
		this.timeType = timeType;
	}
	public TrafficZone getTrafficZone() {
		return trafficZone;
	}
	public void setTrafficZone(TrafficZone trafficZone) {
		this.trafficZone = trafficZone;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public Inspector getInspector() {
		return inspector;
	}
	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}
	public Set<Inspector> getCheckInspectors() {
		return checkInspectors;
	}
	public void setCheckInspectors(Set<Inspector> checkInspectors) {
		this.checkInspectors = checkInspectors;
	}

	
	
}
