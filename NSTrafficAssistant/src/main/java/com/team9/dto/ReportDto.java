package com.team9.dto;

import java.io.Serializable;

import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public class ReportDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private TrafficZone zone;
	private TrafficType trafficType;
	private TimeTicketType time;
	private UserTicketType userType;

	public ReportDto() {
	}

	public ReportDto(String startDate, String endDate, TrafficZone zone, TrafficType trafficType, TimeTicketType time,
			UserTicketType userType) {
		this();
		this.startDate = startDate;
		this.endDate = endDate;
		this.zone = zone;
		this.trafficType = trafficType;
		this.time = time;
		this.userType = userType;
	}

	public UserTicketType getUserType() {
		return userType;
	}

	public void setUserType(UserTicketType userType) {
		this.userType = userType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public TrafficZone getZone() {
		return zone;
	}

	public void setZone(TrafficZone zone) {
		this.zone = zone;
	}

	public TrafficType getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(TrafficType trafficType) {
		this.trafficType = trafficType;
	}

	public TimeTicketType getTime() {
		return time;
	}

	public void setTime(TimeTicketType time) {
		this.time = time;
	}

}
