package com.team9.dto;

import java.util.ArrayList;
import java.util.List;

import com.team9.model.Station;
import com.team9.model.StationLine;
import com.team9.model.TrafficType;

public class StationDTO {
	
	private Long id;
	private String name;
	private TrafficType type;
	private double xCoordinate;
	private double yCoordinate;
	private List<StationLineDto> lines;
	
	public StationDTO(Station s) {
		this.id = s.getId();
		this.name = s.getName();
		this.type = s.getType();
		this.xCoordinate = s.getxCoordinate();
		this.yCoordinate = s.getyCoordinate();
		
		this.lines = new ArrayList<StationLineDto>();
		if(s.getLines() != null) {
			for(StationLine sl : s.getLines()) {
				this.lines.add(new StationLineDto(sl));
			}
		}
		
	}
		

	public StationDTO(String name, TrafficType type, double xCoordinate, double yCoordinate,
			List<StationLineDto> lines) {
		this();
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.lines = lines;
	}



	public StationDTO(Long id, String name, TrafficType type, double xCoordinate, double yCoordinate,
			List<StationLineDto> lines) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.lines = lines;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StationDTO() {}

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

	public List<StationLineDto> getLines() {
		return lines;
	}

	public void setLines(List<StationLineDto> lines) {
		this.lines = lines;
	}
	
	
}
