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
	private String timeType;
	private String trafficZone;
	private String trafficType;
	private double price;

	public TicketDto() {
	}

	public TicketDto(String timeType, String trafficZone, String trafficType, double price) {
		this();
		this.timeType = timeType;
		this.trafficZone = trafficZone;
		this.trafficType = trafficType;
		this.price = price;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getTrafficZone() {
		return trafficZone;
	}

	public void setTrafficZone(String trafficZone) {
		this.trafficZone = trafficZone;
	}

	public String getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(String trafficType) {
		this.trafficType = trafficType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
