package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.NSTrafficAssistant.constants.PriceItemConstants;
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
import com.team9.repository.PriceListRepository;
import com.team9.service.PriceListService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistServiceIntegrationTest {

	@Autowired
	private PriceListService service;

	@Autowired
	private PriceListRepository repository;
	/*
	 * testiramo metodu dodavanja novog cenovnika
	 */

	// 1. testiramo kada nam je cena <= 0

	@Test(expected = PriceLessThanZeroException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenPriceLessThanZero() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_price_less0);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 2. testiramo kada 2 puta posaljemo istu stavku

	@Test(expected = PriceItemAlreadyExistsException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_PriceItemAlreadyExists() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_twice);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 3. testiramo kada je pogresan tip prevoza

	@Test(expected = WrongTrafficTypeException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongTrafficType() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_trafficType);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 4. kada je pogresna vremenska odrednica

	@Test(expected = WrongTicketTimeException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongUserTicketType() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_timeTicket);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 5. kada je pogresna zona

	@Test(expected = WrongTrafficZoneException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongZone() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_zone);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 6. kada je popust > 100 ili < 0
	@Rollback(true)
	@Test(expected = WrongDiscountException.class)
	public void test_addPricelist_whenDiscuountLessZero() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_discount);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	@Test(expected = WrongDiscountException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenDiscountBiggerThan100() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_discount1);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 7. kada nam je broj stvaki razlicit od 24 stavke

	@Test(expected = WrongNumberOfPriceItemException.class)
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongNumberOfItems() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_number);
		PricelistReaderDto result = this.service.addPricelist(p);
	}

	// 8 . KADA JE SVE OK

	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_allOk() throws ParseException, NotFoundActivePricelistException,
			PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException,
			WrongNumberOfPriceItemException {
		PricelistDto p = new PricelistDto(PriceItemConstants.items_normal);
		PricelistReaderDto result = this.service.addPricelist(p);

		assertNotNull(result);
		assertTrue(result.getItems().size() == 24);
		assertTrue(result.getActivate());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		Date issue = new Date(new java.util.Date().getTime());
		assertTrue(fmt.format(result.getIssueDate()).equals(fmt.format(issue)));


		// mozemo i da povucemo sve cenovnike i da vidimo da u bazi imamo 2
		List<PriceList> found = this.repository.findAll();
		assertTrue(found.size() == 2);
	}

	// 9. metoda getValidPricelist()
	@Test
	public void getValidPricelist() throws NotFoundActivePricelistException {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());

		PricelistReaderDto found = this.service.getValidPricelist();

		assertNotNull(found);
		assertTrue(found.getId() == 10);
		assertTrue(found.getIssueDate().equals(issue));
		assertTrue(found.getActivate());
	
	}

}
