package com.team9.dto;

public class StationLineDto {

	private int stationNum;
	private int arrival;
	private String Station;
	
	public StationLineDto(int stationNum, int arrival, String station) {
		super();
		this.stationNum = stationNum;
		this.arrival = arrival;
		Station = station;
	}
	public int getStationNum() {
		return stationNum;
	}
	public void setStationNum(int stationNum) {
		this.stationNum = stationNum;
	}
	public int getArrival() {
		return arrival;
	}
	public void setArrival(int arrival) {
		this.arrival = arrival;
	}
	public String getStation() {
		return Station;
	}
	public void setStation(String station) {
		Station = station;
	}
	
	
}
