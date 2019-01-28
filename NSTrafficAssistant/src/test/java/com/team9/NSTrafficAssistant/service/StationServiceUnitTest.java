package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.StationDTO;
import com.team9.exceptions.InvalidInputFormatException;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Address;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationServiceUnitTest {
	
	@Autowired
	private StationService stationService;
	
	@MockBean
	private StationRepository stationRepositoryMocked;
	
	@Before
	public void setUp() {
		
		Station s2 = new Station(2L, "Balzakova", TrafficType.METRO, 45.0, 19.0, null);
		Station s5 = new Station(5L, "Zeleznicka", TrafficType.METRO, 48.0, 22.0, null);
		
		Mockito.when(stationRepositoryMocked.findByNameAndType("Balzakova", TrafficType.METRO)).thenReturn(s2);
		Mockito.when(stationRepositoryMocked.save(s5)).thenReturn(s5);

		Mockito.when(stationRepositoryMocked.findById(5L)).thenReturn(Optional.of(s5));
		Mockito.when(stationRepositoryMocked.findById(6L)).thenReturn(Optional.empty());
	}
	
	@Test(expected = StationNotFoundException.class)
	public void testUpdateStation_notFound() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(7L, "Bulevar", TrafficType.METRO, 10.0, 15.0, null);

		stationService.updateStation(test);
		
		verify(stationRepositoryMocked, times(1)).findById(test.getId());
	}
	
	@Test(expected = StationAlreadyExistsException.class)
	public void testUpdateStation_exists() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(5L, "Balzakova", TrafficType.METRO, 10.0, 15.0, null);

		stationService.updateStation(test);
		
		verify(stationRepositoryMocked, times(1)).findById(test.getId());
		verify(stationRepositoryMocked, times(1)).findByNameAndType(test.getName(), test.getType());
	}
	
	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateStation_invalidInputName() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(5L, "", TrafficType.METRO, 10.0, 15.0, null);

		stationService.updateStation(test);
	}
	
	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateStation_invalidInputType() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(5L, "Nova ulica", TrafficType.TRAM, 10.0, 15.0, null);

		stationService.updateStation(test);
		
		verify(stationRepositoryMocked, times(1)).findById(test.getId());
	}
	
	@Test
	public void testUpdateStation_allFine() throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(5L, "Bulevar", TrafficType.METRO, 10.0, 15.0, null);
		Station s6 = new Station(5L, "Bulevar", TrafficType.BUS, 10.0, 15.0, null);
		Mockito.when(stationRepositoryMocked.save(s6)).thenReturn(s6);
		
		Station updated = stationService.updateStation(test);	

		assertEquals("Bulevar", updated.getName());
		assertEquals(0, Double.compare(10.0, updated.getxCoordinate()));
		assertEquals(0, Double.compare(15.0, updated.getyCoordinate()));
		
		verify(stationRepositoryMocked, times(1)).findById(test.getId());
	}
	
	@Test(expected = StationAlreadyExistsException.class)
	public void testCreateStation_exists() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(0L, "Balzakova", TrafficType.METRO, 10.0, 15.0, null);
		
		stationService.createStation(test);
	}
	
	@Test(expected = InvalidInputFormatException.class)
	public void testCreateStation_invalidInputName() throws StationAlreadyExistsException, InvalidInputFormatException {
		StationDTO test = new StationDTO(0L, "", TrafficType.METRO, 10.0, 15.0, null);
		
		stationService.createStation(test);
	}
	
	@Test
	public void testCreateStation_allFine() throws StationAlreadyExistsException, InvalidInputFormatException {	
		StationDTO test = new StationDTO(0L, "Bulevar", TrafficType.TRAM, 10.0, 15.0, null);

		Station created = stationService.createStation(test);
		
		assertNull(created); // Null because save method is not mocked, but if it reached save method it means all works.
		verify(stationRepositoryMocked, times(1)).findByNameAndType(test.getName(), test.getType());
		
	}
	
	@Test(expected = StationNotFoundException.class)
	public void testDeleteStation_notFound() throws StationNotFoundException, InvalidInputFormatException {
		stationService.deleteStation(6L);
		
		verify(stationRepositoryMocked, times(1)).findById(6L);
	}
	
}
