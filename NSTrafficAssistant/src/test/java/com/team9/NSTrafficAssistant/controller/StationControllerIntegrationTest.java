package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.*;

import java.util.List;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LoginDto;
import com.team9.dto.StationDTO;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private StationService stationService;
	
	private String token;
	
	@Before
	public void logIn() {
		ResponseEntity<String> result = restTemplate.postForEntity("/user/login", new LoginDto("laralukic", "7777"), String.class);
		token = result.getBody();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<StationDTO[]> responseEntity = restTemplate.exchange("/station/getAll", HttpMethod.GET, httpEntity, StationDTO[].class);
		
		int size = responseEntity.getBody().length;
		StationDTO[] stations = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals("Balzakova", stations[stations.length-1].getName());
		
		assertEquals(6, size);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll_unauthorized() {
		// TODO
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllByType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<StationDTO[]> responseEntity = restTemplate.exchange("/station/getAllByType/BUS", HttpMethod.GET, httpEntity, StationDTO[].class);
		
		int size = responseEntity.getBody().length;
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, size);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllById_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/getById/2", HttpMethod.GET, httpEntity, StationDTO.class);
		
		StationDTO found = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, found.getId().intValue());
		assertEquals("Bazar", found.getName());
		assertEquals(TrafficType.TRAM, found.getType());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllById_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		
		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/getById/0", HttpMethod.GET, httpEntity, StationDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateStation_allFine() throws StationNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		int size = stationService.getAll().size();
		
		StationDTO station = new StationDTO("Bulevar", TrafficType.METRO, 24.0, 48.0, "Bulevar Vojvode Stepe", "Beograd", 11000, null);	
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);
		
		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST, httpEntity, StationDTO.class);

		StationDTO created = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(created);
		assertEquals("Bulevar", created.getName());
		assertEquals(TrafficType.METRO, created.getType());
		assertEquals("Bulevar Vojvode Stepe", created.getAddressName());
		assertEquals("Beograd", created.getAddressCity());
		assertEquals(11000, created.getAddressZip());
				
//		List<Station> stations = stationService.getAll();
//		assertEquals(size+1, stations.size());
//		assertEquals(created.getName(), stations.get(stations.size()-1).getName());
//		assertEquals(created.getType(), stations.get(stations.size()-1).getType());
//		assertEquals(created.getAddressName(), stations.get(stations.size()-1).getAddress().getStreet());
//		assertEquals(created.getAddressCity(), stations.get(stations.size()-1).getAddress().getCity());
//		assertEquals(created.getAddressZip(), stations.get(stations.size()-1).getAddress().getZip().intValue());
		
		
		
		stationService.deleteStation(created.getId());
		
	}
	
	@Test
	public void testCreateStation_exists() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		
		StationDTO station = new StationDTO("Narodnog fronta", TrafficType.BUS, 24.0, 48.0, "Bulevar Vojvode Stepe", "Beograd", 11000, null);	
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);
		
		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST, httpEntity, StationDTO.class);
		
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
	}
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testGetAllByLine() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("X-Auth-Token", token);
//		
//		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
//		
//		ResponseEntity<StationDTO[]> responseEntity = restTemplate.exchange("/station/getAllByLine/1L", HttpMethod.GET, httpEntity, StationDTO[].class);
//		
//		int size = responseEntity.getBody().length;
//		
//		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//		assertEquals(size, 0);
//	}
}
