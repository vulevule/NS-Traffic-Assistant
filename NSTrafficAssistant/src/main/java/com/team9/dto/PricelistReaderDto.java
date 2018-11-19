package com.team9.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PricelistReaderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String issueDate;

	private String expirationDate;
	private Boolean activate;

	private Set<PriceItemDto> items = new HashSet<>();

	public PricelistReaderDto() {
	}

	public PricelistReaderDto(Long id, String issueDate, String expirationDate, Boolean activate,
			Set<PriceItemDto> items) {
		this();
		this.id = id;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.activate = activate;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getActivate() {
		return activate;
	}

	public void setActivate(Boolean activate) {
		this.activate = activate;
	}

	public Set<PriceItemDto> getItems() {
		return items;
	}

	public void setItems(Set<PriceItemDto> items) {
		this.items = items;
	}

}
