package com.team9.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
@Table(name="line")
public class Line implements Serializable{

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
	private TrafficZone zone;
	
	//@Column(nullable = false)
	@OneToMany(fetch = FetchType.LAZY)
	private List<Location> route;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Timetable timeTable;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="line")
	private List<StationLine> stations;
	
	public Line() {}
	
	public Line(String name, TrafficType type, TrafficZone zone, List<Location> route, Timetable timeTable,
			List<StationLine> stations) {
		super();
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.route = route;
		this.timeTable = timeTable;
		this.stations = stations;
	}



	public Line(Long id, String name, TrafficType type, TrafficZone zone, List<Location> route, Timetable timeTable,
			List<StationLine> stations) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.route = route;
		this.timeTable = timeTable;
		this.stations = stations;
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
	public TrafficZone getZone() {
		return zone;
	}
	public void setZone(TrafficZone zone) {
		this.zone = zone;
	}
	
	public List<StationLine> getStations() {
		return stations;
	}

	public void setStations(List<StationLine> stations) {
		this.stations = stations;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<Location> getRoute() {
		return route;
	}


	public void setRoute(List<Location> route) {
		this.route = route;
	}

	public Timetable getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(Timetable timeTable) {
		this.timeTable = timeTable;
	}

}
