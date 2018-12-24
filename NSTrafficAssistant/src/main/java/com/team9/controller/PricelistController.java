package com.team9.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.service.PriceListService;

@RestController
@RequestMapping("/pricelist")
public class PricelistController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PriceListService pricelistService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/addPricelist", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistReaderDto> addPricelist(@RequestBody PricelistDto pricelist) {
		logger.info(">> add pricelist: " + pricelist.getIssueDate());
		PricelistReaderDto pl = null;
		try {
			pl = this.pricelistService.addPricelist(pricelist);
			logger.info("<< add pricelist");
			return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.CREATED);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("<< parse exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundActivePricelistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("<< does not found active pricelist");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (PriceItemAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("<< double price item in pricelist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (PriceLessThanZeroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("<< price less than zero exception");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		

		
	}



}
