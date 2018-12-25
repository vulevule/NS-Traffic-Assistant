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

	private String trafficType;

	private String timeType;

	private String zone;
	
	private double studentDiscount;
	private double handycapDiscount;
	private double seniorDiscount;

	public PriceItemDto() {
	}

	
	public PriceItemDto(double price, String trafficType, String timeType, String zone, double studentDiscount,
			double handycapDiscount, double seniorDiscount) {
		this();
		this.price = price;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.zone = zone;
		this.studentDiscount = studentDiscount;
		this.handycapDiscount = handycapDiscount;
		this.seniorDiscount = seniorDiscount;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	

	public double getStudentDiscount() {
		return studentDiscount;
	}


	public void setStudentDiscount(double studentDiscount) {
		this.studentDiscount = studentDiscount;
	}


	public double getHandycapDiscount() {
		return handycapDiscount;
	}


	public void setHandycapDiscount(double handycapDiscount) {
		this.handycapDiscount = handycapDiscount;
	}


	public double getSeniorDiscount() {
		return seniorDiscount;
	}


	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
	}


	public String getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(String trafficType) {
		this.trafficType = trafficType;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

}
