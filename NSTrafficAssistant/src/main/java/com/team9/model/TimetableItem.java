package com.team9.model;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="timetableItem")
public class TimetableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Time startTime;
	@Column(nullable = false)
	private TimeTableType type;
	
	@ManyToOne
	@JoinColumn(name = "line_id")
	private Line line;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "timetable_id", nullable = false)
	private Timetable timeTable;
	
	public TimetableItem(Time startTime, TimeTableType type, Line line) {
		this();
		this.startTime = startTime;
		this.type = type;
		this.line = line;
	}

	public TimetableItem(Long id, Time startTime, TimeTableType type, Line line) {
		this();
		this.id = id;
		this.startTime = startTime;
		this.type = type;
		this.line = line;
	}

	public TimetableItem() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public TimeTableType getType() {
		return type;
	}

	public void setType(TimeTableType type) {
		this.type = type;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
