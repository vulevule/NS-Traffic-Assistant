package com.team9.dto;

import java.io.Serializable;
import java.util.List;

public class TimetableDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<TimetableItemCreateDto> timetables;

	public TimetableDto() {

	}

	public TimetableDto(List<TimetableItemCreateDto> timetables) {
		this();
		this.timetables = timetables;
	}

	public List<TimetableItemCreateDto> getTimetables() {
		return timetables;
	}

	public void setTimetables(List<TimetableItemCreateDto> timetables) {
		this.timetables = timetables;
	}

}
