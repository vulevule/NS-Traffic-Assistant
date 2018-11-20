package com.team9.dto;

import java.io.Serializable;

import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public class TicketReaderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String serialNo;
	private String issueDate;
	private String expirationDate;
	private UserTicketType userType;
	private TimeTicketType timeType;
	private TrafficZone trafficZone;
	private Boolean active;
	private TrafficType trafficType;
	private Double price;
	private String passenger_username;
	private String inspector_username;

	public TicketReaderDto() {
	}

	public TicketReaderDto(Long id, String serialNo, String issueDate,
			String expirationDate, UserTicketType userType,
			TimeTicketType timeType, TrafficZone trafficZone, 
			Boolean active, TrafficType trafficType, Double price,
			String passenger_username, String inspector_username) {
		this();
		this.id = id;
		this.serialNo = serialNo;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.userType = userType;
		this.timeType = timeType;
		this.trafficZone = trafficZone;
		this.active = active;
		this.trafficType = trafficType;
		this.price = price;
		this.passenger_username = passenger_username;
		this.inspector_username = inspector_username;
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

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
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

	public TrafficType getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(TrafficType trafficType) {
		this.trafficType = trafficType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPassenger_username() {
		return passenger_username;
	}

	public void setPassenger_username(String passenger_username) {
		this.passenger_username = passenger_username;
	}

	public String getInspector_username() {
		return inspector_username;
	}

	public void setInspector_username(String inspector_username) {
		this.inspector_username = inspector_username;
	}

}
