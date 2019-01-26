package com.team9.util;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilLine {
	private String name;
	private String description;
	private ArrayList<UtilLocation> coordinates;
	private ArrayList<String> timeTable;
	@JsonIgnore
	private LinkedHashMap<Stop, Double> stopsAndTimes = new LinkedHashMap<Stop, Double>();
	
	public UtilLine() {}

	public UtilLine(String name, String description, ArrayList<UtilLocation> coordinates, ArrayList<String> timeTable) {
		//super();
		this.name = name;
		this.description = description;
		this.coordinates = coordinates;
		this.timeTable = timeTable;
		this.stopsAndTimes = new LinkedHashMap<Stop, Double>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<UtilLocation> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(ArrayList<UtilLocation> coordinates) {
		this.coordinates = coordinates;
	}

	public ArrayList<String> getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(ArrayList<String> timeTable) {
		this.timeTable = timeTable;
	}
	
	public LinkedHashMap<Stop, Double> getStopsAndTimes() {
		return stopsAndTimes;
	}

	public void setStopsAndTimes(LinkedHashMap<Stop, Double> stopsAndTimes) {
		this.stopsAndTimes = stopsAndTimes;
	}

	@Override
	public String toString() {
		String retVal =  "Line name: " + name + "\nDescription: " + description + "\nCoordiantes:\n" + coordinates + "\nTimetable:\n" + timeTable + "\nStations and times:\n";		
		int i = 1;
		for(Stop s : stopsAndTimes.keySet()) {
			retVal += "\t" + i + ". Station: " + s.getName() + " - Time: " + stopsAndTimes.get(s) + "\n";
			i++;
		}
		return retVal;
	}
	
	
	
}
