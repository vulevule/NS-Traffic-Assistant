package com.team9.dto;

import java.io.Serializable;

import javax.persistence.Column;

import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public class PriceItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double price;
	private UserTicketType userType;
	
	private TrafficType trafficType;

	private TimeTicketType timeType;

	private TrafficZone zone;
	
	public PriceItemDto(){}

	public PriceItemDto(double price, UserTicketType userType, TrafficType trafficType, TimeTicketType timeType,
			TrafficZone zone) {
		this();
		this.price = price;
		this.userType = userType;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.zone = zone;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UserTicketType getUserType() {
		return userType;
	}

	public void setUserType(UserTicketType userType) {
		this.userType = userType;
	}

	public TrafficType getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(TrafficType trafficType) {
		this.trafficType = trafficType;
	}

	public TimeTicketType getTimeType() {
		return timeType;
	}

	public void setTimeType(TimeTicketType timeType) {
		this.timeType = timeType;
	}

	public TrafficZone getZone() {
		return zone;
	}

	public void setZone(TrafficZone zone) {
		this.zone = zone;
	}
	
	

}
