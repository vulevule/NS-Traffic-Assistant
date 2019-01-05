package com.team9.NSTrafficAssistant.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.PriceItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistItemRepositoryIntegrationTest {

	@Autowired
	private PriceItemRepository repository;

	/*
	 * 1. testiramo metodu findByPricelist(long id);
	 */
	// 1.a kada pricelist sa trazenim id-em postoji
	@Test
	public void test_findByPricelist_when_pricelist_exists() {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());

		PriceList pl = new PriceList(new Long(10), issue, null, true);
		Set<PriceItem> found_items = this.repository.findByPricelist(pl);

		assertNotNull(pl);
		assertTrue(found_items.size() == 24);

	}

	// 1.b kada pricelist sa trazenim id-em ne postoji
	@Test
	public void test_findByPricelist_when_pricelist_does_not_exist() {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList pl = new PriceList(new Long(7), issue, null, true);
		Set<PriceItem> found_items = this.repository.findByPricelist(pl);

		assertNotNull(found_items);
		assertTrue(found_items.size() == 0);
	}

	/*
	 * 2. testiramo metodu Optional<PriceItem>
	 * findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType tt,
	 * TimeTicketType time, TrafficZone zone, PriceList id);
	 */

	// 2.a testiramo 2-3 kombinacije koje postoje u bazi i proverimo njihov
	// sadrzaj
	@Test
	public void test_findByTypesAndPricelist_when_items_exists() {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList pl = new PriceList(new Long(10), issue, null, true);
		// bus/second/month/10
		Optional<PriceItem> found_1 = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType.BUS,
				TimeTicketType.MONTH, TrafficZone.SECOND, pl);
		// proverimo da li su dobijeni podaci tacni
		assertNotNull(found_1.get());
		assertTrue("id = 14", found_1.get().getId() == 14);
		assertTrue("traffic type = bus", found_1.get().getTrafficType() == TrafficType.BUS);
		assertTrue("time type = month", found_1.get().getTimeType() == TimeTicketType.MONTH);
		assertTrue("zone = second", found_1.get().getZone() == TrafficZone.SECOND);
		assertTrue("price = 1000", found_1.get().getPrice() == 1000);
		assertTrue(found_1.get().getStudentDiscount() == 10);
		assertTrue(found_1.get().getSeniorDiscount() == 5);
		assertTrue(found_1.get().getHandycapDiscount() == 5);
		assertTrue(found_1.get().getPricelist().getId() == 10);

		// tram/first/annual/10
		Optional<PriceItem> found_2 = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType.TRAM,
				TimeTicketType.ANNUAL, TrafficZone.FIRST, pl);
		// proverimo da li su dobijeni podaci tacni
		assertNotNull(found_2.get());
		assertTrue(found_2.get().getId() == 27);
		assertTrue(found_2.get().getTrafficType() == TrafficType.TRAM);
		assertTrue(found_2.get().getTimeType() == TimeTicketType.ANNUAL);
		assertTrue(found_2.get().getZone() == TrafficZone.FIRST);
		assertTrue(found_2.get().getPrice() == 10000);
		assertTrue(found_2.get().getStudentDiscount() == 10);
		assertTrue(found_2.get().getSeniorDiscount() == 5);
		assertTrue(found_2.get().getHandycapDiscount() == 5);
		assertTrue(found_2.get().getPricelist().getId() == 10);

		// metro/second/single/10
		Optional<PriceItem> found_3 = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType.METRO,
				TimeTicketType.SINGLE, TrafficZone.SECOND, pl);
		// proverimo da li su dobijeni podaci tacni
		assertNotNull(found_3.get());
		assertTrue(found_3.get().getId() == 26);
		assertTrue(found_3.get().getTrafficType() == TrafficType.METRO);
		assertTrue(found_3.get().getTimeType() == TimeTicketType.SINGLE);
		assertTrue(found_3.get().getZone() == TrafficZone.SECOND);
		assertTrue(found_3.get().getPrice() == 100);
		assertTrue(found_3.get().getStudentDiscount() == 10);
		assertTrue(found_3.get().getSeniorDiscount() == 5);
		assertTrue(found_3.get().getHandycapDiscount() == 5);
		assertTrue(found_3.get().getPricelist().getId() == 10);
	}

	// 2.b trazimo stavku cenovnika koja ne postoji
	@Test
	public void test_foundByTypes_whenItemNotExist() {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 22).getTime().getTime());
		PriceList pl = new PriceList(new Long(3), issue, null, true);
		Optional<PriceItem> found = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(TrafficType.TRAM,
				TimeTicketType.ANNUAL, TrafficZone.SECOND, pl);
		assertFalse(found.isPresent());
	}

}
