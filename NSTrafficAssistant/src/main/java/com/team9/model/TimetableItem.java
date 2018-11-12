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
import javax.persistence.ManyToOne;
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

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "timetableItem_line", joinColumns = {
			@JoinColumn(name = "timetableItem_id") }, inverseJoinColumns = { @JoinColumn(name = "line_id") })
	private Set<Line> lines;
	
	public TimetableItem() {
	}

	public TimetableItem(Long id, Time startTime, Set<Line> lines) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.lines = lines;
	}

	public TimetableItem(Time startTime, Set<Line> lines) {
		super();
		this.startTime = startTime;
		this.lines = lines;
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

	public Set<Line> getLines() {
		return lines;
	}

	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

	
}
