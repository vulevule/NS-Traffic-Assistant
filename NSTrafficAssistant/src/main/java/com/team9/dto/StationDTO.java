package com.team9.dto;

import com.team9.model.TrafficType;

public class StationDTO {

	private String name;
	private TrafficType type;
	private double xCoordinate;
	private double yCoordinate;
	private String addressName;
	private String addressCity;
	private int addressZip;
	
	public StationDTO(String name, TrafficType type, double xCoordinate, double yCoordinate, String addressName,
			String addressCity, int addressZip) {
		super();
		this.name = name;
		this.type = type;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.addressName = addressName;
		this.addressCity = addressCity;
		this.addressZip = addressZip;
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
	
	
}
