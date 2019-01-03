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


	private Set<PriceItemDto> items = new HashSet<>();

	public PricelistDto() {
	}

	public PricelistDto(Set<PriceItemDto> items) {
		this();
		this.items = items;
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
