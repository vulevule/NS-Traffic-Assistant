package com.team9.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

public class LineDto {

	private String name;
	private String trafficType;
	private String trafficZone;
	private ArrayList<StationLineDto> stations;
	private Collection<Time> timeTableItems;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrafficType() {
		return trafficType;
	}
	public void setTrafficType(String trafficType) {
		this.trafficType = trafficType;
	}
	public String getTrafficZone() {
		return trafficZone;
	}
	public void setTrafficZone(String trafficZone) {
		this.trafficZone = trafficZone;
	}
	public Collection<StationLineDto> getStations() {
		return stations;
	}
	public void setStations(ArrayList<StationLineDto> stations) {
		this.stations = stations;
	}
	public Collection<Time> getTimeTableItems() {
		return timeTableItems;
	}
	public void setTimeTableItems(Collection<Time> timeTableItems) {
		this.timeTableItems = timeTableItems;
	}	
	
}
