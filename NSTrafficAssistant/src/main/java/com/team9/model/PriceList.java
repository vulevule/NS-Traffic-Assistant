package com.team9.model;

import java.io.Serializable;
import java.sql.Date;
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
public class PriceList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Date issueDate;

	@Column
	private Date expirationDate;

	// jedan cenovnik sadrzi vise stavki, a jedna stavka pripada jednom cenovniku
	@OneToMany(fetch = FetchType.LAZY)
	private Set<PriceItem> items;
	
	@Column
	private boolean activate;

	public PriceList() {
	}

	public PriceList(Long id, Date issueDate, Date expirationDate, Set<PriceItem> items, boolean activate) {
		this();
		this.id = id;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.items = items;
		this.activate = activate;
	}
	
	

	public PriceList(Date issueDate, Date expirationDate, Set<PriceItem> items, boolean activate) {
		this();
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.items = items;
		this.activate = activate;
	}
	
	public PriceList(Date issueDate, Date expirationDate, boolean activate) {
		this();
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.activate = activate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Set<PriceItem> getItems() {
		return items;
	}

	public void setItems(Set<PriceItem> items) {
		this.items = items;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	
}
