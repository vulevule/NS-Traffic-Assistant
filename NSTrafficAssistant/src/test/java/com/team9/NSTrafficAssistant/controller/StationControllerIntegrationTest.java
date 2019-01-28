package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.StationDTO;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private StationRepository stationRepository;

	private String token;

	@Before
	public void logIn() {
		ResponseEntity<LoginUserDto> result = restTemplate.postForEntity("/user/login", new LoginDto("laralukic", "7777"),
				LoginUserDto.class);
		token = result.getBody().getToken();
	}

	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<StationDTO[]> responseEntity = restTemplate.exchange("/station/getAll", HttpMethod.GET,
				httpEntity, StationDTO[].class);

		int size = responseEntity.getBody().length;
		StationDTO[] stations = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals("Bulevar Jase Tomica", stations[stations.length - 1].getName());

		assertEquals(7, size);
	}

	@Test
	public void testCreate_unauthorized() {
		HttpHeaders headers = new HttpHeaders();

		StationDTO station = new StationDTO("Bulevar", TrafficType.METRO, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void testCreate_unauthorizedToken() {
		HttpHeaders headers = new HttpHeaders();
		String token = "xxxxxxxxxxxxx";
		headers.add("X-Auth-Token", token);
		
		StationDTO station = new StationDTO("Bulevar", TrafficType.METRO, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllByType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<StationDTO[]> responseEntity = restTemplate.exchange("/station/getAllByType/BUS", HttpMethod.GET,
				httpEntity, StationDTO[].class);

		int size = responseEntity.getBody().length;

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, size);
	}

	@Test
	public void testGetAllById_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/getById/2", HttpMethod.GET,
				httpEntity, StationDTO.class);

		StationDTO found = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, found.getId().intValue());
		assertEquals("Bazar", found.getName());
		assertEquals(TrafficType.TRAM, found.getType());
	}

	@Test
	public void testGetAllById_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<StationDTO> responseEntity = restTemplate.exchange("/station/getById/0", HttpMethod.GET,
				httpEntity, StationDTO.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	public void testCreateStation_allFine() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		int size = stationRepository.findAll().size();

		StationDTO station = new StationDTO("Bulevar", TrafficType.METRO, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		String message = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Station Bulevar successfully created", message);

		ResponseEntity<StationDTO> createdEntity = restTemplate.exchange("/station/getByNameAndType/" + station.getName() + "/" + station.getType(), HttpMethod.GET,
				httpEntity, StationDTO.class);
		StationDTO created = createdEntity.getBody();
		
		ResponseEntity<StationDTO[]> stationsEntity = restTemplate.exchange("/station/getAll", HttpMethod.GET,
				httpEntity, StationDTO[].class);
		StationDTO[] stations = stationsEntity.getBody();
		
		assertEquals(size+1, stations.length);
		assertEquals(station.getName(), created.getName());
		assertEquals(station.getType(), created.getType());

		ResponseEntity<String> deleteEntity = restTemplate.exchange("/station/delete/" + created.getId(), HttpMethod.DELETE,
				httpEntity, String.class);
		
		assertEquals("Station deleted", deleteEntity.getBody());
		
	}

	@Test
	public void testCreateStation_exists() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO("Narodnog fronta", TrafficType.BUS, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals(station.getType() + " station " + station.getName() + " already exists!", responseEntity.getBody());
	}
	
	@Test
	public void testCreateStation_invalidInputName() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO("", TrafficType.BUS, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}
	
	@Test
	public void testCreateStation_invalidInputCoord() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO("", TrafficType.BUS, 0, 0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/create", HttpMethod.POST,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdateStation_invalidInputName() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO(1L, "", TrafficType.BUS, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/update", HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdateStation_invalidInputType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO(1L, "Nova stanica", TrafficType.METRO, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/update", HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdateStation_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO(10L, "Nova Stanica", TrafficType.BUS, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/update", HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Station " + station.getName() + " not found!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdateStation_exists() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO(1L, "Zeleznicka", TrafficType.BUS, 24.0, 48.0, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/update", HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals(station.getType() + " station " + station.getName() + " already exists!", responseEntity.getBody());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateStation_allFine() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		StationDTO station = new StationDTO(1L, "Bulevar", TrafficType.BUS, 19.830287933873482, 45.26408747364272, null);
		HttpEntity<StationDTO> httpEntity = new HttpEntity<StationDTO>(station, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/station/update", HttpMethod.PUT,
				httpEntity, String.class);

		String message = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Station updated", message);

		ResponseEntity<StationDTO> createdEntity = restTemplate.exchange("/station/getById/" + station.getId(), HttpMethod.GET,
				httpEntity, StationDTO.class);
		StationDTO created = createdEntity.getBody();
		
		assertEquals(station.getName(), created.getName());
		assertEquals(station.getType(), created.getType());
		
	}
	
	// @Test
	// public void testGetAllByLine() {
	// HttpHeaders headers = new HttpHeaders();
	// headers.add("X-Auth-Token", token);
	//
	// HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
	//
	// ResponseEntity<StationDTO[]> responseEntity =
	// restTemplate.exchange("/station/getAllByLine/1L", HttpMethod.GET, httpEntity,
	// StationDTO[].class);
	//
	// int size = responseEntity.getBody().length;
	//
	// assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	// assertEquals(size, 0);
	// }
}
