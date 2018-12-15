package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

import com.team9.model.Address;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;
import com.team9.service.StationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationServiceTest {
	
	@Autowired
	private StationService stationService;
	
	@MockBean
	private StationRepository stationRepositoryMocked;
	
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
		
		Mockito.when(stationRepositoryMocked.findByName("Balzakova")).thenReturn(new ArrayList<Station>(Arrays.asList(s1,s2)));
		Mockito.when(stationRepositoryMocked.findByNameContains("Ba")).thenReturn(new ArrayList<Station>(Arrays.asList(s1,s2,s3)));
		Mockito.when(stationRepositoryMocked.findByName("Futoska")).thenReturn(new ArrayList<Station>(Arrays.asList(s4)));
		Mockito.when(stationRepositoryMocked.findByNameAndType("Zeleznicka", TrafficType.METRO)).thenReturn(s5);
		Mockito.when(stationRepositoryMocked.findByNameAndType("Balzakova", TrafficType.METRO)).thenReturn(s2);
		
		Mockito.when(stationRepositoryMocked.findByType(TrafficType.BUS)).thenReturn(new ArrayList<Station>(Arrays.asList(s1,s3)));
		
		Mockito.when(stationRepositoryMocked.findById(1L)).thenReturn(Optional.of(s1));
		Mockito.when(stationRepositoryMocked.findById(2L)).thenReturn(Optional.of(s2));
		Mockito.when(stationRepositoryMocked.findById(3L)).thenReturn(Optional.of(s3));
		Mockito.when(stationRepositoryMocked.findById(4L)).thenReturn(Optional.of(s4));
		Mockito.when(stationRepositoryMocked.findById(5L)).thenReturn(Optional.of(s5));
	}
	
	@Test
	public void testUpdateStation() {
		Station test = stationRepositoryMocked.findByNameAndType("Zeleznicka", TrafficType.METRO);
		
		test.setName("Bulevar");
		
		assertEquals(Long.valueOf(5L), Long.valueOf(test.getId()));
		
		Station updated = stationService.updateStation(test);
		
		assertNull(updated);
		
//		assertEquals("Bulevar", updated.getName());
//		verify(stationRepositoryMocked, times(1)).findById(5L);
	}
	
	@Test
	public void testCreateStation() {
		Address a3 = new Address("Futoska", "Novi Sad", 21000, null, null);
		Station s4 = new Station(4L, "Futoska", TrafficType.TRAM, 47.0, 21.0, a3, null);
		
		Station created = stationService.createStation(s4);
		assertNull(created);
		
		s4 = new Station(6L, "Narodnog fronta", TrafficType.METRO, 47.0, 21.0, a3, null);
		created = stationService.createStation(s4);
		
		assertEquals("Narodnog fronta", created.getName());
	}
	
	@Test
	public void testDeleteStation() {
		boolean result = stationService.deleteStation(5L);
		
		verify(stationRepositoryMocked, times(1)).findById(5L);
		assertTrue(result);
	}
	
	@Test
	public void testGetByName() {
		ArrayList<Station> result = (ArrayList<Station>) stationService.getByName("Balzakova");
		
		assertEquals(2, result.size());
		verify(stationRepositoryMocked, times(1)).findByName("Balzakova");
	}
	
	@Test
	public void testGetAllByType() {
		ArrayList<Station> result = (ArrayList<Station>) stationService.getAllByType(TrafficType.BUS);
		
		assertEquals(2, result.size());
		verify(stationRepositoryMocked, times(1)).findByType(TrafficType.BUS);
		
//		result = (ArrayList<Station>) stationService.getAllByType(TrafficType.TRAM);
//		
//		assertEquals(0, result.size());
//		verify(stationRepositoryMocked, times(1)).findByType(TrafficType.TRAM);
	}
	
	
}
