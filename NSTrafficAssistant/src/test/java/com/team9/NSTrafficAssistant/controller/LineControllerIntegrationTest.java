package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LineDto;
import com.team9.dto.LocationDto;
import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.StationLineDto;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LineControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private LineRepository lineRepository;

	private String token;

	@Before
	public void logIn() {
		ResponseEntity<LoginUserDto> result = restTemplate.postForEntity("/user/login",
				new LoginDto("laralukic", "7777"), LoginUserDto.class);
		token = result.getBody().getToken();
	}

	@Test
	public void testCreate_unauthorized() {
		HttpHeaders headers = new HttpHeaders();

		LineDto line = new LineDto();
		HttpEntity<LineDto> httpEntity = new HttpEntity<LineDto>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testCreate_unauthorizedToken() {
		HttpHeaders headers = new HttpHeaders();
		String token = "xxxxxxxxxxxxx";
		headers.add("X-Auth-Token", token);

		LineDto line = new LineDto();
		HttpEntity<LineDto> httpEntity = new HttpEntity<LineDto>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	public void testCreate_allFine() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Bazar", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Narodnog fronta", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NOVA", "Skroz dobra linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		int size = lineRepository.findAll().size();

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Line NOVA successfully created", responseEntity.getBody());

		ResponseEntity<LineDto> createdResponse = restTemplate.exchange(
				"/line/getByMarkAndType/" + line.getMark() + "/" + line.getType(), HttpMethod.GET, httpEntity,
				LineDto.class);
		LineDto created = createdResponse.getBody();

		ResponseEntity<LineDto[]> findAllResponse = restTemplate.exchange("/line/getAll", HttpMethod.GET, httpEntity,
				LineDto[].class);
		LineDto[] lines = findAllResponse.getBody();

		assertEquals(size + 1, lines.length);
		assertEquals(line.getMark(), created.getMark());
		assertEquals(line.getName(), created.getName());
		assertEquals(line.getType(), created.getType());
		assertEquals(2, created.getStations().size());
		assertEquals("Bazar", created.getStations().get(0).getStationName());

		// ResponseEntity<String> deleteEntity = restTemplate.exchange("/line/delete/" +
		// created.getId(),
		// HttpMethod.DELETE, httpEntity, String.class);
		//
		// assertEquals("Line deleted", deleteEntity.getBody());
	}

	@Test
	public void testCreate_exists() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("1A", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals(line.getType() + " line " + line.getMark() + " already exists!", responseEntity.getBody());
	}

	@Test
	public void testCreateLine_invalidInputMarkOrName() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());

		line = new LineDto("1A", "x", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		httpEntity = new HttpEntity<Object>(line, headers);
		responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}

	@Test
	public void testCreateLine_invalidInputInsufficentStations() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}

	@Test
	public void testCreateLine_invalidInputDuplicateCoords() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Line can not contain same coordinates in route!", responseEntity.getBody());
	}

	@Test
	public void testCreateLine_invalidInputStationTypeMissmatch() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.METRO, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("BUS station Bazar can not be assigned to METRO line!", responseEntity.getBody());
	}

	@Test
	public void testCreateLine_stationNotFound() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Neka stanica", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 10L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Station Nebitno not found!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdate_allFine() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "1A", "Novi opis", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Line successfully updated", responseEntity.getBody());
	}
	
	@Test
	public void testUpdate_notFound() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(5L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(line.getType() + " line " + line.getMark() + " not found!", responseEntity.getBody());
	}
	
	@Test
	public void testUpdate_exists() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "2A", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals(line.getType() + " line " + line.getMark() + " already exists!", responseEntity.getBody());
	}

	@Test
	public void testUpdateLine_invalidInputMarkOrName() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());

		line = new LineDto("1A", "x", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		httpEntity = new HttpEntity<Object>(line, headers);
		responseEntity = restTemplate.exchange("/line/create", HttpMethod.POST, httpEntity, String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}

	@Test
	public void testUpdateLine_invalidInputInsufficentStations() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Invalid input format!", responseEntity.getBody());
	}

	@Test
	public void testUpdateLine_invalidInputDuplicateCoords() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Line can not contain same coordinates in route!", responseEntity.getBody());
	}

	@Test
	public void testUpdateLine_lineTypeChangeAttempt() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.METRO, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		assertEquals("Line type can not be changed!", responseEntity.getBody());
	}

	@Test
	public void testUpdateLine_stationNotFound() {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Neka stanica", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 10L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(line, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/update", HttpMethod.PUT, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Station Nebitno not found!", responseEntity.getBody());
	}

	@Test
	public void testDelete_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/delete/10", HttpMethod.DELETE, httpEntity,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Line not found!", responseEntity.getBody());
	}
	
	@Test
	public void testDelete_allFine() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/line/delete/3", HttpMethod.DELETE, httpEntity,
				String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Line deleted", responseEntity.getBody());
	}
	
	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto[]> responseEntity = restTemplate.exchange("/line/getAll", HttpMethod.GET, httpEntity,
				LineDto[].class);

		LineDto[] lines = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("1A", lines[0].getMark());
		assertEquals(4, lines.length);
	}

	@Test
	public void testGetByType_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto[]> responseEntity = restTemplate.exchange("/line/getAllByType/BUS", HttpMethod.GET,
				httpEntity, LineDto[].class);

		LineDto[] lines = responseEntity.getBody();

		assertEquals(2, lines.length);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("1A", lines[0].getMark());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines[0].getName());
		assertEquals("2A", lines[1].getMark());
		assertEquals("Test linija", lines[1].getName());
	}

	@Test
	public void testGetByZone_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto[]> responseEntity = restTemplate.exchange("/line/getAllByZone/FIRST", HttpMethod.GET,
				httpEntity, LineDto[].class);

		LineDto[] lines = responseEntity.getBody();

		assertEquals(2, lines.length);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("1A", lines[0].getMark());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines[0].getName());
		assertEquals("1T", lines[1].getMark());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", lines[1].getName());
	}

	@Test
	public void testGetById_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto> responseEntity = restTemplate.exchange("/line/getById/1", HttpMethod.GET, httpEntity,
				LineDto.class);

		LineDto found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertEquals("1A", found.getMark());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", found.getName());
	}

	@Test
	public void testGetById_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto> responseEntity = restTemplate.exchange("/line/getById/0", HttpMethod.GET, httpEntity,
				LineDto.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testGetByMarkAndType_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto> responseEntity = restTemplate.exchange("/line/getByMarkAndType/1T/TRAM", HttpMethod.GET,
				httpEntity, LineDto.class);

		LineDto found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertEquals("1T", found.getMark());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", found.getName());
	}

	@Test
	public void testGetByMarkAndType_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto> responseEntity = restTemplate.exchange("/line/getByMarkAndType/10/BUS", HttpMethod.GET,
				httpEntity, LineDto.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testGetByStation_found() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto[]> responseEntity = restTemplate.exchange("/line/getAllByStation/1", HttpMethod.GET,
				httpEntity, LineDto[].class);

		LineDto[] lines = responseEntity.getBody();

		assertEquals(2, lines.length);
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines[0].getName());
		assertEquals("1A", lines[0].getMark());
		assertEquals(TrafficType.BUS, lines[0].getType());
		assertEquals(TrafficZone.FIRST, lines[0].getZone());
		assertEquals(2, lines[0].getStations().size());
		assertEquals("Bazar", lines[0].getStations().get(0).getStationName());
	}

	@Test
	public void testGetByStation_notFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<LineDto> responseEntity = restTemplate.exchange("/line/getAllByStation/0", HttpMethod.GET,
				httpEntity, LineDto.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

}
