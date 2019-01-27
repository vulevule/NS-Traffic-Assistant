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
	private String mark;
	private String name;
	private TrafficType type;
	private TrafficZone zone;
	private List<StationLineDto> stations;	
	private List<LocationDto> route;
	
	public LineDto() {}

	public LineDto(String mark, String name, TrafficType type, TrafficZone zone, List<StationLineDto> stations,
			List<LocationDto> route) {
		super();
		this.mark = mark;
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.stations = stations;
		this.route = route;
	}

	public LineDto(Long id, String mark, String name, TrafficType type, TrafficZone zone, List<StationLineDto> stations,
			List<LocationDto> route) {
		super();
		this.id = id;
		this.mark = mark;
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.stations = stations;
		this.route = route;
	}

	public LineDto(Line l) {
		this.id = l.getId();
		this.mark = l.getMark();
		this.name = l.getName();
		this.type = l.getType();
		this.zone = l.getZone();
		
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

	public List<LocationDto> getRoute() {
		return route;
	}

	public void setRoute(List<LocationDto> route) {
		this.route = route;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "LineDto [id=" + id + ", mark=" + mark + ", name=" + name + ", type=" + type + ", zone=" + zone
				+ ", stations=" + stations + ", route=" + route + "]";
	}
	
	
	
}
