package com.team9.service;

import java.text.ParseException;

import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
import com.team9.model.PriceList;

public interface PriceListService {
	
	PriceList getPricelist() throws NotFoundActivePricelistException;

	PricelistReaderDto getValidPricelist() throws NotFoundActivePricelistException;
	

	PricelistReaderDto addPricelist(PricelistDto pricelist) throws ParseException, NotFoundActivePricelistException, PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException;

}
