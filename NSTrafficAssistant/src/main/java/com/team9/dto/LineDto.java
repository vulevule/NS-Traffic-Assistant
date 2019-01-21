package com.team9.dto;

import java.util.ArrayList;
import java.util.List;

import com.team9.model.Line;
import com.team9.model.Location;
import com.team9.model.StationLine;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public class LineDto {

	private Long id;
	private String name;
	private TrafficType type;
	private TrafficZone zone;
	private List<StationLineDto> stations;	
	private Long timeTable;
	private List<LocationDto> route;
	
	public LineDto() {}
	
	public LineDto(String name, TrafficType type, TrafficZone zone, List<StationLineDto> stations, Long timeTable,
			List<LocationDto> route) {
		this();
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.stations = stations;
		this.timeTable = timeTable;
		this.route = route;
	}

	public LineDto(Long id, String name, TrafficType type, TrafficZone zone, List<StationLineDto> stations,
			Long timeTable, List<LocationDto> route) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.stations = stations;
		this.timeTable = timeTable;
		this.route = route;
	}

	public LineDto(Line l) {
		this.id = l.getId();
		this.name = l.getName();
		this.type = l.getType();
		this.zone = l.getZone();
		this.timeTable = l.getTimeTable().getId();
		
		this.route = new ArrayList<LocationDto>();
		for(Location loc : l.getRoute()) {
			this.route.add(new LocationDto(loc.getLat(), loc.getLon()));
		}
		
		this.stations = new ArrayList<StationLineDto>();
		for(StationLine sl : l.getStations()) {
			this.stations.add(new StationLineDto(sl));
		}
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

	public TrafficZone getZone() {
		return zone;
	}

	public void setZone(TrafficZone zone) {
		this.zone = zone;
	}

	public List<StationLineDto> getStations() {
		return stations;
	}

	public void setStations(List<StationLineDto> stations) {
		this.stations = stations;
	}

	public Long getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(Long timeTable) {
		this.timeTable = timeTable;
	}

	public List<LocationDto> getRoute() {
		return route;
	}

	public void setRoute(List<LocationDto> route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "LineDto [id=" + id + ", name=" + name + ", type=" + type + ", zone=" + zone + ", stations=" + stations
				+ ", timeTable=" + timeTable + ", route=" + route + "]";
	}
	
	
	
}
