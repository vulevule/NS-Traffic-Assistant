package com.team9.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stationLine")
public class StationLine implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int stationNum;
	
	@Column(nullable = false)
	private int arrival;
	
	// Jedna stanica moze imati vise StationLine a jedan StationLine se odnosi samo na jednu stanicu
	@ManyToOne(optional=false)
	private Station station;
	
	@ManyToOne(optional=false)
	private Line line;
	
	public StationLine(int stationNum, int arrival, Station station, Line line) {
		super();
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}

	public StationLine(Long id, int stationNum, int arrival, Station station, Line line) {
		super();
		this.id = id;
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}

	public StationLine() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStationNum() {
		return stationNum;
	}

	public void setStationNum(int stationNum) {
		this.stationNum = stationNum;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	
	
	
}
