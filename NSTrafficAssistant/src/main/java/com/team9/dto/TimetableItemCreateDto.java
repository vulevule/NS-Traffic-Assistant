package com.team9.dto;

import java.io.Serializable;

public class TimetableItemCreateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String line_mark;
	private String line_name;
	private String workdayTimes;
	private String sundayTimes;
	private String saturdayTimes;

	public TimetableItemCreateDto() {

	}

	public TimetableItemCreateDto(String line_mark, String line_name, String workdayTimes, String sundayTimes,
			String saturdayTimes) {
		this();
		this.line_mark = line_mark;
		this.line_name = line_name;
		this.workdayTimes = workdayTimes;
		this.sundayTimes = sundayTimes;
		this.saturdayTimes = saturdayTimes;
	}

	public String getLine_mark() {
		return line_mark;
	}

	public void setLine_mark(String line_mark) {
		this.line_mark = line_mark;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getWorkdayTimes() {
		return workdayTimes;
	}

	public void setWorkdayTimes(String workdayTimes) {
		this.workdayTimes = workdayTimes;
	}

	public String getSundayTimes() {
		return sundayTimes;
	}

	public void setSundayTimes(String sundayTimes) {
		this.sundayTimes = sundayTimes;
	}

	public String getSaturdayTimes() {
		return saturdayTimes;
	}

	public void setSaturdayTimes(String saturdayTimes) {
		this.saturdayTimes = saturdayTimes;
	}

}
