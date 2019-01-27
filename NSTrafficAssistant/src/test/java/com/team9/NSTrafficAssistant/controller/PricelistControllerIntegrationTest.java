package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.NSTrafficAssistant.constants.PriceItemConstants;
import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PricelistControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private String token;
	
	@Before
	public void login(){
		ResponseEntity<LoginUserDto> result = restTemplate.postForEntity("/user/login", new LoginDto("laralukic", "7777"), LoginUserDto.class);
		token = result.getBody().getToken();
	}

	
	/*
	 * testiramo metodu add pricelist:
	 */
	
	//1. kada je cena manja od 0
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenPriceLessThan0(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_price_less0);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("The price of the item is less than 0!"));
	}
	
	//2. kada imamo 2 iste stavke
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenSameItems(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_twice);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("The price list must not contain the same items!"));
	}
	
	//3. kada je pogresan tip prevoza 
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongTrafficType(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_trafficType);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect traffic type: "));
	}
	
	//4. kada je pogresna zona
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongZone(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_zone);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect traffic zone: "));
	}
	
	//5. kada je pogresno vreme 
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongTicketTime(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_timeTicket);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect time ticket: "));
	}
	
	//6. kada imamo manje ili vise stavki od 24
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongNumberOfItems(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_number);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("The price list has more than or equal to 24 items!"));
	}
	
	//7. kada popust nije izmedju o i 100
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_whenWrongDiscount(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_wrong_discount);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("The discount must be between 0 and 100!"));
	}
	
	//kada je sve ok
	@Test
	@Transactional
	@Rollback(true)
	public void test_addPricelist_OK(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		PricelistDto p = new PricelistDto(PriceItemConstants.items_normal);
		HttpEntity<PricelistDto> httpEntity = new HttpEntity<PricelistDto>(p, headers);
		
		ResponseEntity<String> result = restTemplate.exchange("/pricelist/addPricelist",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.CREATED);
		assertEquals(result.getBody(), "The price list has been successfully added!");
		
	}
	
	//testiramo metodu koja nam vraca cenovnik
	@Test
	public void test_getPriceList(){
		ResponseEntity<PricelistReaderDto> result = restTemplate.getForEntity("/pricelist/getPricelist", PricelistReaderDto.class);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result);
	}
	
}
