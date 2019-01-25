package com.team9.util;

import java.util.ArrayList;

public class Stop {
	private String name;
	private double lat;
	private double lon;
	private ArrayList<UtilLine> lines;
	
	public Stop() {}

	public Stop(String name, double lat, double lon, ArrayList<UtilLine> lines) {
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

	public ArrayList<UtilLine> getLines() {
		return lines;
	}

	public void setLines(ArrayList<UtilLine> lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		String retVal =  "Stop name: " + name + "\nLat: " + lat + "\nLon: " + lon + "\nLines:";
		for(UtilLine l : lines) {
			retVal += "\n\t" + l.getName();
		}
		
		return retVal;
	}
	
	
}
