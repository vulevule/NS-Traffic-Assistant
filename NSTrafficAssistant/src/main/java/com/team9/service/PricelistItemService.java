package com.team9.service;

import java.util.Set;

import com.team9.dto.PriceItemDto;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;

public interface PricelistItemService {
	
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList savepl);

	public Set<PriceItemDto> convertToDto(Set<PriceItem> items);


	public Set<PriceItem> getItemsByPricelist(PriceList pl);

	public void deleteItems(Set<PriceItem> items);
	
}
