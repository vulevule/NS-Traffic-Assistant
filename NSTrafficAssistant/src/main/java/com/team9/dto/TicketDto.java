package com.team9.dto;

import java.io.Serializable;

import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public class TicketDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeTicketType timeType;
	private TrafficZone trafficZone;
	private TrafficType trafficType;
	private double price;

	public TicketDto() {
	}

	public TicketDto(TimeTicketType timeType, TrafficZone trafficZone, TrafficType trafficType, double price) {
		this();
		this.timeType = timeType;
		this.trafficZone = trafficZone;
		this.trafficType = trafficType;
		this.price = price;
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

	public TrafficType getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(TrafficType trafficType) {
		this.trafficType = trafficType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
