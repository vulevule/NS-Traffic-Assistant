package com.team9.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	private double discount;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
			name = "priceItem_priceList",
			joinColumns = {@JoinColumn(name="priceItem_id")},
			inverseJoinColumns = {@JoinColumn(name="priceList_id")})
	private Set<PriceList> priceLists = new HashSet<>();

	public PriceItem() {
	}

	public PriceItem(Long id, double price, UserTicketType userType, TrafficType trafficType, TimeTicketType timeType,
			double discount, Set<PriceList> priceLists) {
		this();
		this.id = id;
		this.price = price;
		this.userType = userType;
		this.trafficType = trafficType;
		this.timeType = timeType;
		this.discount = discount;
		this.priceLists = priceLists;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Set<PriceList> getPriceLists() {
		return priceLists;
	}

	public void setPriceLists(Set<PriceList> priceLists) {
		this.priceLists = priceLists;
	}
	
	

}
