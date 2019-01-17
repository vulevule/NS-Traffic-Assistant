package com.team9.dto;

import com.team9.model.StationLine;

public class StationLineDto {
	
	private Long id;
	private int stationNum;
	private int arrival;
	private Long stationId;
	private Long lineId;
	
	public StationLineDto() {}

	public StationLineDto(int stationNum, int arrival, Long stationId, Long lineId) {
		this();
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.stationId = stationId;
		this.lineId = lineId;
	}

	public StationLineDto(Long id, int stationNum, int arrival, Long stationId, Long lineId) {
		this();
		this.id = id;
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.stationId = stationId;
		this.lineId = lineId;
	}
	
	public StationLineDto(StationLine sl) {
		this.id = sl.getId();
		this.stationNum = sl.getStationNum();
		this.arrival = sl.getArrival();
		this.stationId = sl.getStation().getId();
		this.lineId = sl.getLine().getId();
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

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
}
