package com.team9.service;

import java.util.Set;

import com.team9.dto.PriceItemDto;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public interface PricelistItemService {
	
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList savepl) throws  PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException;

	public Set<PriceItemDto> convertToDto(Set<PriceItem> items);


	public Set<PriceItem> getItemsByPricelist(PriceList pl);

	public void deleteItems(Set<PriceItem> items);
	
	
	public PriceItem getPriceItem(TrafficType type, TimeTicketType time, TrafficZone zone, PriceList p_id) throws PriceItemNotFoundException;
}
