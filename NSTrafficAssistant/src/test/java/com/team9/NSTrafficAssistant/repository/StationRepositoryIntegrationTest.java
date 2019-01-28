package com.team9.NSTrafficAssistant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class StationRepositoryIntegrationTest {
	
	@Autowired
	public StationRepository stationRepository;
	
	@Test
	public void testFindByName_found() {
		List<Station> stations = stationRepository.findByName("Bazar");
		
		assertEquals(2, stations.size());
		assertEquals("Bazar", stations.get(0).getName());
		assertEquals("Bazar", stations.get(1).getName());
		assertEquals(TrafficType.BUS, stations.get(0).getType());
		assertEquals(TrafficType.TRAM, stations.get(1).getType());
	}
	
	@Test
	public void testFindByName_notFound() {
		List<Station> stations = stationRepository.findByName("dfg");
		assertEquals(0, stations.size());
	}
	
	@Test
	public void testFindByNameContains_found() {
		List<Station> stations = stationRepository.findByNameContains("Ba");
		
		assertEquals(4, stations.size());
		
		assertEquals("Bazar", stations.get(0).getName());
		assertEquals(TrafficType.BUS, stations.get(0).getType());
		//assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.0);
		
		assertEquals("Bazar", stations.get(1).getName());
		assertEquals(TrafficType.TRAM, stations.get(1).getType());
		//assertThat(stations.get(1).getxCoordinate()).isEqualTo(19.0);
		
		assertEquals("Bazar-Pothodnik", stations.get(2).getName());
		assertEquals(TrafficType.METRO, stations.get(2).getType());
		//assertThat(stations.get(2).getxCoordinate()).isEqualTo(20.0);
		
		assertEquals("Balzakova", stations.get(3).getName());
		assertEquals(TrafficType.METRO, stations.get(3).getType());
		//assertThat(stations.get(3).getxCoordinate()).isEqualTo(23.0);

	}
	
	@Test
	public void testFindByNameContains_notFound() {
		List<Station> stations = stationRepository.findByNameContains("dfg");
		assertEquals(0, stations.size());
	}
	
	@Test
	public void testFindByNameAndType_found() {
		Station station = stationRepository.findByNameAndType("Zeleznicka", TrafficType.BUS);
		
		assertEquals("Zeleznicka", station.getName());
		assertEquals(TrafficType.BUS, station.getType());
		//assertThat(station.getxCoordinate()).isEqualTo(22.0);
		//assertThat(station.getyCoordinate()).isEqualTo(48.0);
	}
	
	@Test
	public void testFindByNameAndType_notFound() {
		Station station = stationRepository.findByNameAndType("dfg", TrafficType.BUS);
		assertNull(station);
	}
	
	@Test
	public void testFindByType_found() {
		List<Station> stations = stationRepository.findByType(TrafficType.METRO);
		
		assertEquals(2, stations.size());
		
		assertEquals("Bazar-Pothodnik", stations.get(0).getName());
		assertEquals(TrafficType.METRO, stations.get(0).getType());
		//assertThat(stations.get(0).getxCoordinate()).isEqualTo(20.0);
		
		assertEquals("Balzakova", stations.get(1).getName());
		assertEquals(TrafficType.METRO, stations.get(1).getType());
		//assertThat(stations.get(1).getxCoordinate()).isEqualTo(23.0);
	}
	
}
