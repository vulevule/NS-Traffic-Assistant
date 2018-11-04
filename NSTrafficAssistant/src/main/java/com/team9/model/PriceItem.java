package com.team9.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="price_items")
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
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="price_items")
	private Set<PriceList> priceLists;
	
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
	
	
	

}
