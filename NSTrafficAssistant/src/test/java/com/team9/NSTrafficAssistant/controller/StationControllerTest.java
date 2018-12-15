package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Address;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private StationService stationServiceMocked;
	
	@Before
	public void setUp() {
		Address a1 = new Address("Balzakova", "Novi Sad", 21000, null, null);
		Address a2 = new Address("Bazar", "Novi Sad", 21000, null, null);
		Address a3 = new Address("Futoska", "Novi Sad", 21000, null, null);
		Address a4 = new Address("Zeleznicka", "Novi Sad", 21000, null, null);
		
		Station s1 = new Station(1L, "Balzakova", TrafficType.BUS, 45.0, 19.0, a1, null);
		Station s2 = new Station(2L, "Balzakova", TrafficType.METRO, 45.0, 19.0, a1, null);
		Station s3 = new Station(3L, "Bazar", TrafficType.BUS, 46.0, 20.0, a2, null);
		Station s4 = new Station(4L, "Futoska", TrafficType.TRAM, 47.0, 21.0, a3, null);
		Station s5 = new Station(5L, "Zeleznicka", TrafficType.METRO, 48.0, 22.0, a4, null);
		
		Mockito.when(stationServiceMocked.getAllByType(TrafficType.BUS)).thenReturn(new ArrayList<Station>(Arrays.asList(s1,s2)));
		
	
	}
	
	@Test
	public void testGetAllByType() {
		ResponseEntity<List> response = restTemplate.getForEntity("/station/getAllByType/BUS", List.class);
		List stations = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, stations.size());
	}
}
