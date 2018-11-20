package com.team9.service;

import java.text.ParseException;

import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.model.PriceList;

public interface PriceListService {

	PricelistReaderDto getValidPricelist();

	PricelistReaderDto addPricelist(PricelistDto pricelist) throws ParseException;

	PricelistReaderDto changePricelist(PricelistDto pricelist);

	PricelistReaderDto changeActivatePricelist(PricelistDto pricelist);
}
