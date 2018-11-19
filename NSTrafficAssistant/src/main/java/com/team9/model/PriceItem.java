package com.team9.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class PriceItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private double price;
	@Column
	private UserTicketType userType;
	@Column
	private TrafficType trafficType;
	@Column
	private TimeTicketType timeType;

	@Column
	private TrafficZone zone;

	//stavka pripada jednom cenovniku
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="pricelist_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private PriceList pricelist;

	public PriceItem() {
	}

	public PriceItem(Long id, double price, UserTicketType userType, TrafficType trafficType, TimeTicketType timeType,
			PriceList pricelist, TrafficZone zone) {
		this();
		this.id = id;
		this.price = price;
		this.userType = userType;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.pricelist = pricelist;
		this.zone = zone;
	}

	public PriceItem(double price, UserTicketType userType, TrafficType trafficType, TimeTicketType timeType,
			PriceList pricelist, TrafficZone zone) {
		this();
		this.price = price;
		this.userType = userType;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.pricelist = pricelist;
		this.zone = zone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	

	public PriceList getPricelist() {
		return pricelist;
	}

	public void setPricelist(PriceList pricelist) {
		this.pricelist = pricelist;
	}

	public TrafficZone getZone() {
		return zone;
	}

	public void setZone(TrafficZone zone) {
		this.zone = zone;
	}

}
