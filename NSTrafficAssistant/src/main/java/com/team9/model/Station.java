package com.team9.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "station")
public class Station implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private TrafficType type;
	@Column(nullable = false)
	private Integer xCoordinate;
	@Column(nullable = false)
	private Integer yCoordinate;
	
	@ManyToOne(optional=false)
	private Address address;
	
	//stanica moze da pripada vise linija, linija sadrzi vise stanica 
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="station_line", joinColumns = { @JoinColumn(name="station_id")}, inverseJoinColumns = {@JoinColumn(name="line_id")})
	private Set<Line> lines;
	
	public Station() {}

	
	public Station(Long id, String name, TrafficType type, Integer xCoordinate, Integer yCoordinate, Address address,
			Set<Line> lines) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.address = address;
		this.lines = lines;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrafficType getType() {
		return type;
	}

	public void setType(TrafficType type) {
		this.type = type;
	}

	public Integer getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Integer getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	

}
