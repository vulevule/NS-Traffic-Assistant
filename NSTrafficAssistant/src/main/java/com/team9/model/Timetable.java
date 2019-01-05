package com.team9.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "timetable")
public class Timetable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private Date expirationDate; 
	@Column
	private Date issueDate;
	
	//red voznje sadrzi vise stavki, stavka pripada u vise redova voznje
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "workdayItems", joinColumns = {
			@JoinColumn(name = "timetable_id") }, inverseJoinColumns = { @JoinColumn(name = "timetableItem_id") })
	private Set<TimetableItem> workdayItems;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "saturdayItems", joinColumns = {
			@JoinColumn(name = "timetable_id") }, inverseJoinColumns = { @JoinColumn(name = "timetableItem_id") })
	private Set<TimetableItem> saturdayItems;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "sundayItems", joinColumns = {
			@JoinColumn(name = "timetable_id") }, inverseJoinColumns = { @JoinColumn(name = "timetableItem_id") })
	private Set<TimetableItem> sundayItems;

	
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
