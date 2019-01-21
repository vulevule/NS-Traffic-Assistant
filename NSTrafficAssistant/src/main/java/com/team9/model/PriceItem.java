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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private double price;

	@Column
	private TrafficType trafficType;
	@Column
	private TimeTicketType timeType;

	@Column
	private TrafficZone zone;

	@Column
	private double studentDiscount;
	@Column
	private double seniorDiscount;
	@Column
	private double handycapDiscount;

	// stavka pripada jednom cenovniku
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "pricelist_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private PriceList pricelist;

	public PriceItem() {
	}

	public PriceItem(Long id, double price, TrafficType trafficType, TimeTicketType timeType, TrafficZone zone,
			double studentDiscount, double seniorDiscount, double handycapDiscont, PriceList pricelist) {
		this();
		this.id = id;
		this.price = price;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.zone = zone;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.handycapDiscount = handycapDiscont;
		this.pricelist = pricelist;
	}

	public PriceItem(double price, TrafficType trafficType, TimeTicketType timeType, TrafficZone zone,
			double studentDiscount, double seniorDiscount, double handycapDiscont, PriceList pricelist) {
		super();
		this.price = price;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.zone = zone;
		this.studentDiscount = studentDiscount;
		this.seniorDiscount = seniorDiscount;
		this.handycapDiscount = handycapDiscont;
		this.pricelist = pricelist;
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

	public double getStudentDiscount() {
		return studentDiscount;
	}

	public void setStudentDiscount(double studentDiscount) {
		this.studentDiscount = studentDiscount;
	}

	public double getSeniorDiscount() {
		return seniorDiscount;
	}

	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
	}

	
	public double getHandycapDiscount() {
		return handycapDiscount;
	}

	public void setHandycapDiscount(double handycapDiscount) {
		this.handycapDiscount = handycapDiscount;
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
