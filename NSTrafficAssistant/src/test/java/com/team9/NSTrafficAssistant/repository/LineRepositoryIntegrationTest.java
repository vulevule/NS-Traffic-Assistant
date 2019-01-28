package com.team9.NSTrafficAssistant.repository;

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

import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LineRepositoryIntegrationTest {

	@Autowired
	public LineRepository lineRepository;

	@Test
	public void testGetAll() {
		List<Line> lines = lineRepository.findAll();
		assertEquals(2, lines.size());
		
	}
	
	@Test
	public void testFindByName_found() {
		List<Line> lines = lineRepository.findByName("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA");

		assertEquals(1, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	public void testFindByName_notFound() {
		List<Line> lines = lineRepository.findByName("asdfgdfsg");
		assertEquals(0, lines.size());
	}

	@Test
	public void testFindByType_found() {
		List<Line> lines = lineRepository.findByType(TrafficType.BUS);

		assertEquals(1, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());

		lines = lineRepository.findByType(TrafficType.TRAM);

		assertEquals(1, lines.size());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", lines.get(0).getName());
		assertEquals("1T", lines.get(0).getMark());
		assertEquals(TrafficType.TRAM, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bulevar Jase Tomica", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	public void testFindByZone_found() {
		List<Line> lines = lineRepository.findByZone(TrafficZone.FIRST);
		
		assertEquals(2, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	public void testFindByMarkAndType_found() {
		Line line = lineRepository.findByMarkAndType("1A", TrafficType.BUS);
		
		assertNotNull(line);
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", line.getName());
		assertEquals("1A", line.getMark());
		assertEquals(TrafficType.BUS, line.getType());
		assertEquals(TrafficZone.FIRST, line.getZone());
		assertEquals(2, line.getStations().size());
		assertEquals("Bazar", line.getStations().get(0).getStation().getName());
		
		line = lineRepository.findByMarkAndType("1T", TrafficType.TRAM);
		
		assertNotNull(line);
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", line.getName());
		assertEquals("1T", line.getMark());
		assertEquals(TrafficType.TRAM, line.getType());
		assertEquals(TrafficZone.FIRST, line.getZone());
		assertEquals(2, line.getStations().size());
		assertEquals("Bulevar Jase Tomica", line.getStations().get(0).getStation().getName());
	}

	@Test
	public void testFindByMarkAndType_notFound() {
		Line line = lineRepository.findByMarkAndType("BB", TrafficType.BUS);	
		assertNull(line);
	}

	@Test
	public void testFindById_found() {
		Line line = lineRepository.findById(1L).orElse(null);
		
		assertNotNull(line);
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", line.getName());
		assertEquals("1A", line.getMark());
		assertEquals(TrafficType.BUS, line.getType());
		assertEquals(TrafficZone.FIRST, line.getZone());
		assertEquals(2, line.getStations().size());
		assertEquals("Bazar", line.getStations().get(0).getStation().getName());
	}

	@Test
	public void testFindById_notFound() {
		Line line = lineRepository.findById(10L).orElse(null);
		assertNull(line);
	}

	@Test
	public void testFindByTypeAndZone_found() {
		List<Line> lines = lineRepository.findByTypeAndZone(TrafficType.TRAM, TrafficZone.FIRST);
		
		assertEquals(1, lines.size());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", lines.get(0).getName());
		assertEquals("1T", lines.get(0).getMark());
		assertEquals(TrafficType.TRAM, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bulevar Jase Tomica", lines.get(0).getStations().get(0).getStation().getName());
	}
}
