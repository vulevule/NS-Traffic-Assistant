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

public class PriceList implements Serializable{

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
	
	//jedan cenovnik sadrzi vise stavki, a jedna stavka pripada u vise cenovnika 
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="priceList_priceListItem", joinColumns = { @JoinColumn(name="priceList_id")}, inverseJoinColumns = {@JoinColumn(name="priceListItem_id")})
	private Set<PriceItem> items;
	
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
	
	

}
