package com.team9.dto;

public class LocationDto {

	private Double lat;
	private Double lon;
	
	public LocationDto() {
		// TODO Auto-generated constructor stub
	}

	public LocationDto(Double lat, Double lon) {
		this();
		this.lat = lat;
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "LocationDto [lat=" + lat + ", lon=" + lon + "]";
	}
	
	

}
