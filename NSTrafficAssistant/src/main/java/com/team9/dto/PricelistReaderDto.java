package com.team9.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PricelistReaderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date issueDate;

	private Date expirationDate;
	private Boolean activate;

	private List<PriceItemDto> items = new ArrayList<>();

	public PricelistReaderDto() {
	}

	public PricelistReaderDto(Long id, Date issueDate, Date expirationDate, Boolean activate,
			List<PriceItemDto> items) {
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

	public Boolean getActivate() {
		return activate;
	}

	public void setActivate(Boolean activate) {
		this.activate = activate;
	}

	public List<PriceItemDto> getItems() {
		return items;
	}

	public void setItems(List<PriceItemDto> items) {
		this.items = items;
	}

}
