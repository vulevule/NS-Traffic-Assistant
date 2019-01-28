package com.team9.NSTrafficAssistant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.StationDTO;
import com.team9.exceptions.InvalidInputFormatException;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationServiceIntegrationTest {

	@Autowired
	private StationService stationService;

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateStation_allFine() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(0L, "Bulevar", TrafficType.TRAM, 10.0, 15.0, null);

		Station found = stationService.createStation(station);

		assertEquals("Bulevar", found.getName());
		assertEquals(TrafficType.TRAM, found.getType());
	}

	@Test(expected = StationAlreadyExistsException.class)
	public void testCreateStation_exists() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(0L, "Bazar", TrafficType.BUS, 10.0, 15.0, null);

		stationService.createStation(station);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateStation_invalidInputName() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(0L, "", TrafficType.BUS, 10.0, 15.0, null);

		stationService.createStation(station);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateStation_invalidInputType() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(0L, "", null, 10.0, 15.0, null);

		stationService.createStation(station);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateStation_allFine() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(6L, "Bulevar", TrafficType.METRO, 10.0, 15.0, null);

		Station found = stationService.updateStation(station);

		assertEquals("Bulevar", found.getName());
		assertEquals(TrafficType.METRO, found.getType());
	}
	
	@Test(expected = StationNotFoundException.class)
	public void testUpdateStation_notFound() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(8L, "Bulevar", TrafficType.METRO, 10.0, 15.0, null);

		stationService.updateStation(station);

	}
	
	@Test(expected = StationAlreadyExistsException.class)
	public void testUpdateStation_exists() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(1L, "Zeleznicka", TrafficType.BUS, 10.0, 15.0, null);

		stationService.updateStation(station);
	}
	
	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateStation_invalidInputType() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(1L, "Nova stanica", TrafficType.TRAM, 10.0, 15.0, null);

		stationService.updateStation(station);
	}
	
	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateStation_invalidInputName() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO station = new StationDTO(1L, "", TrafficType.TRAM, 10.0, 15.0, null);

		stationService.updateStation(station);
	}
	
	@Test(expected = StationNotFoundException.class)
	public void testDeleteStation_notFound() throws StationNotFoundException, InvalidInputFormatException {
		stationService.deleteStation(8L);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteStation_allFine() throws StationNotFoundException, InvalidInputFormatException {
		boolean result = stationService.deleteStation(5L);
		
		assertTrue(result);
	}

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
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.830287933873482);

		assertEquals("Bazar", stations.get(1).getName());
		assertEquals(TrafficType.TRAM, stations.get(1).getType());
		assertThat(stations.get(1).getxCoordinate()).isEqualTo(19.83210754551692);

		assertEquals("Bazar-Pothodnik", stations.get(2).getName());
		assertEquals(TrafficType.METRO, stations.get(2).getType());
		assertThat(stations.get(2).getxCoordinate()).isEqualTo(19.835214616439767);

		assertEquals("Balzakova", stations.get(3).getName());
		assertEquals(TrafficType.METRO, stations.get(3).getType());
		assertThat(stations.get(3).getxCoordinate()).isEqualTo(19.841394424962346);
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

		assertEquals("Bazar-Pothodnik", stations.get(0).getName());
		assertEquals(TrafficType.METRO, stations.get(0).getType());
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.835214616439767);

		assertEquals("Balzakova", stations.get(1).getName());
		assertEquals(TrafficType.METRO, stations.get(1).getType());
		assertThat(stations.get(1).getxCoordinate()).isEqualTo(19.841394424962346);

		stations = stationService.getAllByType(TrafficType.TRAM);

		assertEquals(2, stations.size());

		assertEquals("Bazar", stations.get(0).getName());
		assertEquals(TrafficType.TRAM, stations.get(0).getType());
		assertThat(stations.get(0).getxCoordinate()).isEqualTo(19.83210754551692);
	}

	@Test
	public void testGetByNameAndType_Found() {
		Station station = stationService.getByNameAndType("Zeleznicka", TrafficType.BUS);

		assertEquals("Zeleznicka", station.getName());
		assertEquals(TrafficType.BUS, station.getType());
		assertThat(station.getxCoordinate()).isEqualTo(19.839197158813473);
		assertThat(station.getyCoordinate()).isEqualTo(45.24794333819497);
	}

	@Test
	public void testGetByNameAndType_notFound() {
		Station station = stationService.getByNameAndType("dfg", TrafficType.BUS);
		assertNull(station);
	}

	@Test
	public void testGetAll() {
		List<Station> stations = stationService.getAll();

		assertEquals(stations.size(), 7);
	}

	@Test
	public void testGetById_Found() {
		Station station = stationService.getById(2L);
		assertNotNull(station);

		assertEquals("Bazar", station.getName());
		assertEquals(TrafficType.TRAM, station.getType());
		assertThat(station.getxCoordinate()).isEqualTo(19.83210754551692);
		assertThat(station.getyCoordinate()).isEqualTo(45.26066810367371);
	}

	@Test
	public void testGetById_notFound() {
		Station station = stationService.getById(0L);
		assertNull(station);
	}

}
