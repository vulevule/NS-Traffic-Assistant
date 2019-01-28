package com.team9.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	@Column(nullable = false)
	private Date issueDate;
	// ima vise item-a, a item ima jedan timetable
	@OneToMany(fetch = FetchType.EAGER)
	private Set<TimetableItem> items;

	@Column(nullable = false)
	private boolean activate;

	public Timetable() {
	}

	public Timetable(Long id, Date expirationDate, Date issueDate, Set<TimetableItem> items) {
		this();
		this.id = id;
		this.expirationDate = expirationDate;
		this.issueDate = issueDate;
		this.items = items;
	}

	public Timetable(Date expirationDate, Date issueDate, Set<TimetableItem> items) {
		this();
		this.expirationDate = expirationDate;
		this.issueDate = issueDate;
		this.items = items;

	}

	public Timetable(Date expirationDate, Date issueDate, boolean activate) {
		this();
		this.expirationDate = expirationDate;
		this.issueDate = issueDate;
		this.activate = activate;
	}

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

	public Set<TimetableItem> getItems() {
		return items;
	}

	public void setItems(Set<TimetableItem> items) {
		this.items = items;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

}
