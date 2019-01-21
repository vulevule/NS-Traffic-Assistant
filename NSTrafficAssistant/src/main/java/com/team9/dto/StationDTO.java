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
	private String addressName;
	private String addressCity;
	private int addressZip;
	private List<StationLineDto> lines;
	
	public StationDTO(Station s) {
		this.id = s.getId();
		this.name = s.getName();
		this.type = s.getType();
		this.xCoordinate = s.getxCoordinate();
		this.yCoordinate = s.getyCoordinate();
		
		if(s.getAddress() != null) {
			this.addressName = s.getAddress().getStreet();
			this.addressCity = s.getAddress().getCity();
			this.addressZip = s.getAddress().getZip();
		}
		
		this.lines = new ArrayList<StationLineDto>();
		if(s.getLines() != null) {
			for(StationLine sl : s.getLines()) {
				this.lines.add(new StationLineDto(sl));
			}
		}
		
	}
		
	public StationDTO(String name, TrafficType type, double xCoordinate, double yCoordinate, String addressName,
			String addressCity, int addressZip, List<StationLineDto> lines) {
		this();
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.addressName = addressName;
		this.addressCity = addressCity;
		this.addressZip = addressZip;
		this.lines = lines;
	}

	public StationDTO(Long id, String name, TrafficType type, double xCoordinate, double yCoordinate,
			String addressName, String addressCity, int addressZip, List<StationLineDto> lines) {
		this();
		this.id = id;
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.addressName = addressName;
		this.addressCity = addressCity;
		this.addressZip = addressZip;
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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public int getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(int addressZip) {
		this.addressZip = addressZip;
	}

	public List<StationLineDto> getLines() {
		return lines;
	}

	public void setLines(List<StationLineDto> lines) {
		this.lines = lines;
	}
	
	
}
