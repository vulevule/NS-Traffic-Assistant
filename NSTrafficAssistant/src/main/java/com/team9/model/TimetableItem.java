package com.team9.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="timetableItem")
public class TimetableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Time startTime;
	@Column(nullable = false)
	private Time endTime;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "timetableItem_line", joinColumns = {
			@JoinColumn(name = "timetableItem_id") }, inverseJoinColumns = { @JoinColumn(name = "line_id") })
	private Set<Line> lines;

	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="timetableItems")
	private Set<Timetable> timetables;
	
	public TimetableItem() {
	}

	public TimetableItem(Time startTime, Time endTime, Set<Line> lines) {
		this();
		this.startTime = startTime;
		this.endTime = endTime;
		this.lines = lines;
	}

	public TimetableItem(Long id, Time startTime, Time endTime, Set<Line> lines) {
		this();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lines = lines;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Line> getLines() {
		return lines;
	}

	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

}
