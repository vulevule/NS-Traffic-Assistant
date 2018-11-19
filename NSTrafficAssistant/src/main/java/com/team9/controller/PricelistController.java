package com.team9.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.service.PriceListService;

@RestController
@RequestMapping("/pricelist")
public class PricelistController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PriceListService pricelistService;
	
	@PostMapping(value="/addPricelist", consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistReaderDto> addPricelist(@RequestBody PricelistDto pricelist){
		logger.info(">> add pricelist: " + pricelist.getIssueDate());
		PricelistReaderDto pl = null;
		try {
			pl = this.pricelistService.addPricelist(pricelist);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pl == null){
			return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.BAD_REQUEST);
		}
		
		logger.info("<< add pricelist");
		return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.CREATED);
	}

	@PostMapping(value="/changePricelist", consumes ="application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistReaderDto> changePricelist(@RequestBody PricelistDto pricelist){
		logger.info(">> change pricelist with id: " + pricelist.getId());
		
		PricelistReaderDto pl = null;
		pl = this.pricelistService.changePricelist(pricelist);
		
		if(pl == null){
			return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.BAD_REQUEST);
		}
		
		logger.info("<< change pricelist with id: " + pricelist.getId());
		
		return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/changeActivate", consumes ="application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PricelistReaderDto> changeActivatePricelist(@RequestBody PricelistDto pricelist){
		logger.info(">> change activate pricelist with id: " + pricelist.getId());
		
		PricelistReaderDto pl = null;
		pl = this.pricelistService.changeActivatePricelist(pricelist);
		
		if(pl == null){
			return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.BAD_REQUEST);
		}
		
		logger.info("<< change activate pricelist with id: " + pricelist.getId());
		
		return new ResponseEntity<PricelistReaderDto>(pl, HttpStatus.OK);
	}
		
}
