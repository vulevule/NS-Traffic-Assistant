package com.team9.util;

import java.util.ArrayList;

public class StopDTO {
	private String name;
	private double lat;
	private double lon;
	private ArrayList<String> lines;
	
	public StopDTO() {}

	public StopDTO(String name, double lat, double lon, ArrayList<String> lines) {
		//super();
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.lines = lines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public ArrayList<String> getLines() {
		return lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof StopDTO)
	    {
			StopDTO temp = (StopDTO) obj;
	        if(this.name.equals(temp.name) && this.lat == temp.lat && this.lon == temp.lon)
	            return true;
	    }
	    return false;
	}
	
	
}
