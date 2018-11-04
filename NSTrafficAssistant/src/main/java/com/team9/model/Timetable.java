package com.team9.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public class Timetable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Date expirationDate; 
	@Column
	private Date issueDate;
	
	//red voznje sadrzi vise stavki, stavka pripada u vise redova voznje
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "timetable_line", joinColumns = {
			@JoinColumn(name = "timetable_id") }, inverseJoinColumns = { @JoinColumn(name = "timetableItem_id") })
	private Set<TimetableItem> timetableItems;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	
	
	
}
