package com.team9.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PricelistDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String issueDate;

	private Set<PriceItemDto> items = new HashSet<>();

	public PricelistDto() {
	}

	public PricelistDto(String issueDate, Set<PriceItemDto> items) {
		this();
		this.issueDate = issueDate;
		this.items = items;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public Set<PriceItemDto> getItems() {
		return items;
	}

	public void setItems(Set<PriceItemDto> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
