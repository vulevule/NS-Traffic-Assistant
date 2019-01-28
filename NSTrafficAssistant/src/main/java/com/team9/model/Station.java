package com.team9.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "station")
public class Station implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private TrafficType type;
	@Column(nullable = false)
	private double xCoordinate;
	@Column(nullable = false)
	private double yCoordinate;
	
	//stanica moze da pripada vise linija, linija sadrzi vise stanica 
	@OneToMany(fetch = FetchType.EAGER, mappedBy="station", cascade=CascadeType.ALL)
	private List<StationLine> stationLines;
	
	public Station() {}
	
	public Station(Long id, String name, TrafficType type, double xCoordinate, double yCoordinate, 
			List<StationLine> stationLines) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.stationLines = stationLines;
	}

	public Station(String name, TrafficType type, double xCoordinate, double yCoordinate, 
			List<StationLine> stationLines) {
		super();
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.stationLines = stationLines;
	}

	public List<StationLine> getLines() {
		return stationLines;
	}


	public void setLines(List<StationLine> stationLines) {
		this.stationLines = stationLines;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
