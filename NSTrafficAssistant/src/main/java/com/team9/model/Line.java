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
@Table(name="lines")
public class Line implements Serializable{

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
	private TrafficZone zone;
	
	//linija moze da pripada vise redova voznje, jedan red voznje moze da ima vise linija
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="lines")
	private Set<TimetableItem> timetableItems;
	
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="lines")
	private Set<Station> stations;
	
	public Line() {}
	
	
	public Line(String name, TrafficType type, TrafficZone zone, Set<TimetableItem> timetableItems,
			Set<Station> stations) {
		this();
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.timetableItems = timetableItems;
		this.stations = stations;
	}
	public Line(Long id, String name, TrafficType type, TrafficZone zone, Set<TimetableItem> timetableItems,
			Set<Station> stations) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.timetableItems = timetableItems;
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
	public Set<TimetableItem> getTimetableItems() {
		return timetableItems;
	}
	public void setTimetableItems(Set<TimetableItem> timetableItems) {
		this.timetableItems = timetableItems;
	}
	
	
	

}
