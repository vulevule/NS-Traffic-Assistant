package com.team9.dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class TimeTableItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Time> sundayTimes;
	private ArrayList<Time> saturdayTimes;
	private ArrayList<Time> workdayTimes;
	private Long line_id;
	private String line_name;
	private Long timeTable_id;

	public TimeTableItemDto(ArrayList<Time> sundayTimes, ArrayList<Time> saturdayTimes, ArrayList<Time> workdayTimes,
			Long line_id, String line_name, Long timeTable_id) {
		super();
		this.sundayTimes = sundayTimes;
		this.saturdayTimes = saturdayTimes;
		this.workdayTimes = workdayTimes;
		this.line_id = line_id;
		this.line_name = line_name;
		this.timeTable_id = timeTable_id;
	}

	public ArrayList<Time> getSundayTimes() {
		return sundayTimes;
	}

	public void setSundayTimes(ArrayList<Time> sundayTimes) {
		this.sundayTimes = sundayTimes;
	}

	public ArrayList<Time> getSaturdayTimes() {
		return saturdayTimes;
	}

	public void setSaturdayTimes(ArrayList<Time> saturdayTimes) {
		this.saturdayTimes = saturdayTimes;
	}

	public ArrayList<Time> getWorkdayTimes() {
		return workdayTimes;
	}

	public void setWorkdayTimes(ArrayList<Time> workdayTimes) {
		this.workdayTimes = workdayTimes;
	}

	public Long getLine_id() {
		return line_id;
	}

	public void setLine_id(Long line_id) {
		this.line_id = line_id;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public Long getTimeTable_id() {
		return timeTable_id;
	}

	public void setTimeTable_id(Long timeTable_id) {
		this.timeTable_id = timeTable_id;
	}

}
