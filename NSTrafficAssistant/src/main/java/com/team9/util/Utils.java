package com.team9.util;

import com.mysql.jdbc.util.VersionFSHierarchyMaker;

public class Utils {
	 
	
	public static double timeForBus(UtilLocation l1, UtilLocation l2) {
		double vehicleSpeed = 20.0; // in kmph
		double value = distance(l1, l2) / vehicleSpeed;
		value = Math.round(value * 60 * 100) / 100.0;
		return value;
	}
	
	public static double distance(UtilLocation l1, UtilLocation l2) {
		
		double lat1 = l1.getLat();
		double lon1 = l1.getLon();
		double lat2 = l2.getLat();
		double lon2 = l2.getLon();
		
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			
			return dist;
		}
	}
}
