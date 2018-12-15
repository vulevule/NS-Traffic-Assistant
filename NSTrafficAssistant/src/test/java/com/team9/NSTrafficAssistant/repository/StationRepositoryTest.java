package com.team9.NSTrafficAssistant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.model.Address;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
//@ContextConfiguration(locations = "classpath:test.properties")
public class StationRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	public StationRepository stationRepository;
	
	@Before
	public void setUp() {
		Address a1 = new Address("Balzakova", "Novi Sad", 21000, null, null);
		Address a2 = new Address("Bazar", "Novi Sad", 21000, null, null);
		Address a3 = new Address("Futoska", "Novi Sad", 21000, null, null);
		Address a4 = new Address("Zeleznicka", "Novi Sad", 21000, null, null);
		
		this.entityManager.persist(a1);
		this.entityManager.persist(a2);
		this.entityManager.persist(a3);
		this.entityManager.persist(a4);
		
		this.entityManager.persist(new Station("Balzakova", TrafficType.BUS, 45.0, 19.0, a1, null));
		this.entityManager.persist(new Station("Balzakova", TrafficType.METRO, 45.0, 19.0, a1, null));
		this.entityManager.persist(new Station("Bazar", TrafficType.BUS, 46.0, 20.0, a2, null));
		this.entityManager.persist(new Station("Futoska", TrafficType.TRAM, 47.0, 21.0, a3, null));
		this.entityManager.persist(new Station("Zeleznicka", TrafficType.METRO, 48.0, 22.0, a4, null));
	}
	
	@Test
	public void testFindByName() {
		ArrayList<Station> stations = (ArrayList<Station>) stationRepository.findByName("Balzakova");
		
		assertEquals(2, stations.size());
		assertEquals(TrafficType.BUS, stations.get(0).getType());
	}
	
	@Test
	public void testFindByNameContains() {
		ArrayList<Station> stations = (ArrayList<Station>) stationRepository.findByNameContains("Ba");
		
		assertEquals(3, stations.size());
		assertEquals("Balzakova", stations.get(0).getName());
		assertEquals("Bazar", stations.get(2).getName());
	}
	
	@Test
	public void testFindByNameAndType() {
		Station station = stationRepository.findByNameAndType("Futoska", TrafficType.TRAM);
		
		assertThat(station.getxCoordinate()).isEqualTo(47.0);
	}
	
	@Test
	public void testFindByType() {
		ArrayList<Station> stations = (ArrayList<Station>) stationRepository.findByType(TrafficType.METRO);
		
		assertEquals(2, stations.size());
		assertEquals("Balzakova", stations.get(0).getAddress().getStreet());
		assertEquals("Zeleznicka", stations.get(1).getAddress().getStreet());
	}
}
