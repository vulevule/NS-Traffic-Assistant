package com.team9.NSTrafficAssistant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationServiceIntegrationTest {

	@Autowired
	private StationService stationService;
	
	@Test
	public void testGetByName_found() {
		List<Station> stations = stationService.getByName("Bazar");
		
		assertEquals(2, stations.size());
		assertEquals("Bazar", stations.get(0).getName());
		assertEquals("Bazar", stations.get(1).getName());
		assertEquals(TrafficType.BUS, stations.get(0).getType());
		assertEquals(TrafficType.TRAM, stations.get(1).getType());
		
	}
	
	@Test
	public void testGetByName_notFound() {
		List<Station> stations = stationService.getByName("dfg");
		assertEquals(0, stations.size());
	}
	
	@Test
	public void testGetByNameContains_found() {
		List<Station> stations = stationService.getByNameContains("Ba");
		
		assertEquals(4, stations.size());
		
		assertEquals("Bazar", stations.get(0).getName());
		assertEquals(TrafficType.BUS, stations.get(0).getType());
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.0);
		
		assertEquals("Bazar", stations.get(1).getName());
		assertEquals(TrafficType.TRAM, stations.get(1).getType());
		assertThat(stations.get(1).getxCoordinate()).isEqualTo(19.0);
		
		assertEquals("Bazar-Podhodnik", stations.get(2).getName());
		assertEquals(TrafficType.METRO, stations.get(2).getType());
		assertThat(stations.get(2).getxCoordinate()).isEqualTo(20.0);
		
		assertEquals("Balzakova", stations.get(3).getName());
		assertEquals(TrafficType.METRO, stations.get(3).getType());
		assertThat(stations.get(3).getxCoordinate()).isEqualTo(23.0);
	}
	
	@Test
	public void testGetByNameContains_notFound() {
		List<Station> stations = stationService.getByNameContains("dfg");
		assertEquals(0, stations.size());
	}
	
	@Test
	public void testGetAllByType() {
		List<Station> stations = stationService.getAllByType(TrafficType.METRO);
		
		assertEquals(2, stations.size());
		
		assertEquals("Bazar-Podhodnik", stations.get(0).getName());
		assertEquals(TrafficType.METRO, stations.get(0).getType());
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(20.0);
		
		assertEquals("Balzakova", stations.get(1).getName());
		assertEquals(TrafficType.METRO, stations.get(1).getType());
		assertThat(stations.get(1).getxCoordinate()).isEqualTo(23.0);
		
		stations = stationService.getAllByType(TrafficType.TRAM);
		
		assertEquals(1, stations.size());
		
		assertEquals("Bazar", stations.get(0).getName());
		assertEquals(TrafficType.TRAM, stations.get(0).getType());
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.0);
	}
	
	@Test
	public void testGetByNameAndType_Found() {
		Station station = stationService.getByNameAndType("Zeleznicka", TrafficType.BUS);
		
		assertEquals("Zeleznicka", station.getName());
		assertEquals(TrafficType.BUS, station.getType());
		assertThat(station.getxCoordinate()).isEqualTo(22.0);
		assertThat(station.getyCoordinate()).isEqualTo(48.0);
	}
	
	@Test
	public void testGetByNameAndType_notFound() {
		Station station = stationService.getByNameAndType("dfg", TrafficType.BUS);
		assertNull(station);
	}
	
	@Test
	public void testGetAll() {
		List<Station> stations = stationService.getAll();

		assertEquals(stations.size(), 6);
	}
	
	@Test
	public void testGetById_Found() {
		Station station = stationService.getById(2L);
		assertNotNull(station);
		
		assertEquals("Bazar", station.getName());
		assertEquals(TrafficType.TRAM, station.getType());
		assertThat(station.getxCoordinate()).isEqualTo(19.0);
		assertThat(station.getyCoordinate()).isEqualTo(48.0);
	}
	
	@Test
	public void testGetById_notFound() {
		Station station = stationService.getById(0L);
		assertNull(station);
	}

}
