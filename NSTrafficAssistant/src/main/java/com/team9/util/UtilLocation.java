package com.team9.util;

public class UtilLocation {
	private double lat;
	private double lon;
	
	public UtilLocation() {}
	
	public UtilLocation(double lat, double lon) {
		//super();
		this.lat = lat;
		this.lon = lon;
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

	@Override
	public String toString() {
		return "\n\tlat: " + lat + "\tlon: " + lon;
	}

	@Override
	public boolean equals(Object obj) {	
		if (obj == this) {
		      return true;
		    }
		    if (!(obj instanceof UtilLocation)) {
		      return false;
		    }
		    UtilLocation cc = (UtilLocation)obj;
		    return cc.lat == lat && cc.lon == lon;
	}

	@Override
	public int hashCode() {
		return Double.valueOf(lat+lon).hashCode();
	}
	
	
	
}
