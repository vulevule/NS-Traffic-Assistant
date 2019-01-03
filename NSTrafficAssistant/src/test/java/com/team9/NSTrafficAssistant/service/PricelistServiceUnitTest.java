package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.NSTrafficAssistant.constants.PriceItemConstants;
import com.team9.dto.PricelistReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.model.PriceList;
import com.team9.repository.PriceListRepository;
import com.team9.service.PriceListService;

import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PricelistServiceUnitTest {

	@Autowired
	private PriceListService service;
	
	@MockBean 
	private PriceListRepository pricelist_repo_mock;
	
	
	/*
	 * 1. test kada trazimo aktivan cenovnik i on postoji, vrati nam taj cenovnik
	 * 
	 */
	

	@Test
	public void test_findAcvtivatePricelist_returnPricelist() throws NotFoundActivePricelistException{
		Mockito.when(this.pricelist_repo_mock.findByActivateTrue()).thenReturn(Optional.of(PriceItemConstants.p));
		PriceList p = this.service.getPricelist();
		assertNotNull(p);
		assertTrue(p.isActivate());
	}
	/*
	@Test(expected = NotFoundActivePricelistException.class)
	public void test_findActivatePricelist_throwException() throws NotFoundActivePricelistException{
		Mockito.when(this.pricelist_repo_mock.findByActivateTrue()).thenThrow(NotFoundActivePricelistException.class);
		PricelistReaderDto p = this.service.getValidPricelist();
	}*/
	
}
