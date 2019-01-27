package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.service.PricelistItemService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistItemServiceIntegrationTest {

	@Autowired
	private PricelistItemService service;
	
	//DODAVANJE STAVKI CEMO TESTIRATI, KADA DODAJEMO CENOVNIK
	
	//1.1 testiramo metodu getPriceItem, koja nam vraca stavku na osnovu zadatih parametara
	@Test
	public void test_getPriceItem_whenItemFound() throws PriceItemNotFoundException{
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList p = new PriceList(new Long(10), issue, null, true);
		PriceItem found = this.service.getPriceItem(TrafficType.METRO, TimeTicketType.ANNUAL, TrafficZone.FIRST, p);
		
		assertNotNull(found);
		assertTrue(found.getTrafficType() == TrafficType.METRO);
		assertTrue(found.getTimeType() == TimeTicketType.ANNUAL);
		assertTrue(found.getZone() == TrafficZone.FIRST);
	}
	//1.2 kada trazena stavka ne postoji
	@Test(expected = PriceItemNotFoundException.class)
	public void test_getPriceItem_whenItemNotFound() throws PriceItemNotFoundException{
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList p = new PriceList(new Long(13), issue, null, true);
		PriceItem found = this.service.getPriceItem(TrafficType.METRO, TimeTicketType.ANNUAL, TrafficZone.FIRST, p);
		
	}
	
	//kada vracamo sve stavke od jednog cenovnika
	@Test
	public void test_getAllPriceItemByPricelist() {
		Date issue = new java.sql.Date(new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime().getTime());
		PriceList p = new PriceList(new Long(10), issue, null, true);
		Set<PriceItem> result = this.service.getPriceItemsByPricelist(p);
		assertNotNull(result);
		assertTrue(result.size() == 24);
	}
}
