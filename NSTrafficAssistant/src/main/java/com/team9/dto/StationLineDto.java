package com.team9.dto;

import com.team9.model.StationLine;

public class StationLineDto {
	
	private Long id;
	private int stationNum;
	private int arrival;
	private Long stationId;
	private Long lineId;
	private String stationName;
	private String lineName;
	private String lineMark;
	
	public StationLineDto() {}

	public StationLineDto(int stationNum, int arrival, Long stationId, Long lineId, String stationName,
			String lineName, String lineMark) {
		this();
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.stationId = stationId;
		this.lineId = lineId;
		this.stationName = stationName;
		this.lineName = lineName;
		this.lineMark = lineMark;
	}

	public StationLineDto(Long id, int stationNum, int arrival, Long stationId, Long lineId, String stationName,
			String lineName, String lineMark) {
		this();
		this.id = id;
		this.stationNum = stationNum;
		this.arrival = arrival;
		this.stationId = stationId;
		this.lineId = lineId;
		this.stationName = stationName;
		this.lineName = lineName;
		this.lineMark = lineMark;
	}

	public StationLineDto(StationLine sl) {
		this.id = sl.getId();
		this.stationNum = sl.getStationNum();
		this.arrival = sl.getArrival();
		this.stationId = sl.getStation().getId();
		this.lineId = sl.getLine().getId();
		this.stationName = sl.getStation().getName();
		this.lineName = sl.getLine().getName();
		this.lineMark = sl.getLine().getMark();
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineMark() {
		return lineMark;
	}

	public void setLineMark(String lineMark) {
		this.lineMark = lineMark;
	}

	@Override
	public String toString() {
		return "StationLineDto [id=" + id + ", stationNum=" + stationNum + ", arrival=" + arrival + ", stationId="
				+ stationId + ", lineId=" + lineId + ", stationName=" + stationName + ", lineName=" + lineName
				+ ", lineMark=" + lineMark + "]";
	}
	
	
	
	
	
}
