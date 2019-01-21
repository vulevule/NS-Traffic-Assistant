package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.NSTrafficAssistant.constants.PriceItemConstants;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.PriceItemRepository;
import com.team9.service.PricelistItemService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistItemServiceUnitTest {

	@Autowired
	private PricelistItemService pricelistItemService;

	@MockBean
	private PriceItemRepository priceItemRepository_mock;

	
	/*
	 * 1. kada je cena neke stavke manja od 0 
	 */
	@Test(expected = PriceLessThanZeroException.class)
	public void addPricelistItem_whenPriceLessThanZero() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_price_less0, PriceItemConstants.p);
		
	}

	/*
	 * 2. kada je pogresan tip prevoza
	 * 
	 */
	@Test(expected = WrongTrafficTypeException.class)
	public void addPricelistItem_whenWrongTrafficType() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_trafficType, PriceItemConstants.p);

	}
	
	/*
	 * 3. kada je pogresan vremenski tip  karte
	 */
	
	@Test(expected = WrongTicketTimeException.class)
	public void addPricelistItem_whenWrongTimeTicketType() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_timeTicket, PriceItemConstants.p);

	}
	
	/*
	 * 4. kada je pogresna zona
	 */
	@Test(expected = WrongTrafficZoneException.class)
	public void addPricelistItem_whenWrongZone() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_zone, PriceItemConstants.p);

	}
	
	/*
	 * 5. kada nam je jedan od popusta manji od 0 ili veci od 100
	 */
	
	@Test(expected = WrongDiscountException.class)
	public void addPricelistItem_whenDiscount() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_discount, PriceItemConstants.p);

	}
	
	
	/*
	 * 6. kada je popust veci od 100%
	 */
	
	@Test(expected = WrongDiscountException.class)
	public void addPricelistItem_whenDiscountGreaterThan100() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_discount1, PriceItemConstants.p);

	}
	
	/*
	 * 7. kada imamo manje stavki od 24
	 */
	@Test(expected = WrongNumberOfPriceItemException.class)
	public void addPricelistItem_whenWrongNumber() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_wrong_number, PriceItemConstants.p);

	}
	
	/*
	 * 8. kada vec imamo istu stavku 
	 */
	@Test(expected = PriceItemAlreadyExistsException.class)
	public void addPricelistItem_whenItemExists() throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException{
		Mockito.when(this.priceItemRepository_mock.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType.BUS, TimeTicketType.MONTH, TrafficZone.FIRST, PriceItemConstants.p)).thenReturn(Optional.of(PriceItemConstants.i));

		PriceList p = this.pricelistItemService.addPricelistItems(PriceItemConstants.items_normal, PriceItemConstants.p);

	}
	/*
	 * 9. izracunavanje cene karte
	 */
	@Test
	public void test_calculatePrice(){
		double price = this.pricelistItemService.calculateTicketPrice(1000, 10);
		assertTrue(price == 900);
	}
}
