package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LineDto;
import com.team9.dto.LocationDto;
import com.team9.dto.StationLineDto;
import com.team9.exceptions.InvalidInputFormatException;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Line;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.service.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LineServiceIntegrationTest {

	@Autowired
	private LineService lineService;

	@Test(expected = LineAlreadyExistsException.class)
	public void testCreateLine_exists()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("1A", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateLine_invalidInputMark()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateLine_invalidInputName()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("1A", "x", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateLine_invalidInputInsufficentStations()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateLine_invalidInputDuplicateCoords()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testCreateLine_invalidInputStationTypeMissmatch()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.METRO, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test(expected = StationNotFoundException.class)
	public void testCreateLine_stationNotFound()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 10L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.createLine(line);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateLine_allFine()
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto("NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		Line created = lineService.createLine(line);
		
		assertNotNull(created);
		assertEquals(line.getMark(), created.getMark());
		assertEquals(line.getName(), created.getName());
		assertEquals(line.getType(), created.getType());
		assertEquals(line.getZone(), created.getZone());
	}

	@Test(expected = LineNotFoundException.class)
	public void testUpdateLine_lineNotFound() throws LineNotFoundException, StationNotFoundException,
			LineAlreadyExistsException, InvalidInputFormatException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(5L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = LineAlreadyExistsException.class)
	public void testUpdateLine_exists() throws LineAlreadyExistsException, StationNotFoundException,
			InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "2A", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateLine_invalidInputMark() throws LineAlreadyExistsException, StationNotFoundException,
			InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateLine_invalidInputName() throws LineAlreadyExistsException, StationNotFoundException,
			InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "1A", "x", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateLine_invalidInputInsufficentStations() throws LineAlreadyExistsException,
			StationNotFoundException, InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	@Transactional
	public void testUpdateLine_invalidInputDuplicateCoords() throws LineAlreadyExistsException,
			StationNotFoundException, InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.843186438956764, 45.24886502908012);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	public void testUpdateLine_invalidInputLineTypeChangeAtempt() throws LineAlreadyExistsException,
			StationNotFoundException, InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.METRO, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = InvalidInputFormatException.class)
	@Transactional
	public void testUpdateLine_invalidInputStationTypeMissmatch() throws LineAlreadyExistsException,
			StationNotFoundException, InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 7L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 2L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = StationNotFoundException.class)
	@Transactional
	public void testUpdateLine_stationNotFound() throws LineAlreadyExistsException, StationNotFoundException,
			InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 10L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateLine_allFine() throws LineAlreadyExistsException, StationNotFoundException,
			InvalidInputFormatException, LineNotFoundException {
		StationLineDto sl1 = new StationLineDto(1, 0, 1L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		StationLineDto sl2 = new StationLineDto(1, 0, 4L, 0L, "Nebitno", "Nebitno", "Takodje nebitno");
		List<StationLineDto> stations = new ArrayList<StationLineDto>(Arrays.asList(sl1, sl2));
		LocationDto l1 = new LocationDto(19.843186438956764, 45.24886502908012);
		LocationDto l2 = new LocationDto(19.839238225977166, 45.2479921840457);
		LocationDto l3 = new LocationDto(19.837025702145183, 45.25202058846409);
		List<LocationDto> route = new ArrayList<LocationDto>(Arrays.asList(l1, l2, l3));

		LineDto line = new LineDto(1L, "NL", "Nova linija", TrafficType.BUS, TrafficZone.SECOND, stations, route);
		lineService.updateLine(line);
	}

	@Test(expected = LineNotFoundException.class)
	public void testDeleteLine_notFound() throws LineNotFoundException {
		lineService.deleteLine(10L);

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteLine_allFine() throws LineNotFoundException {
		boolean result = lineService.deleteLine(1L);

		assertTrue(result);
	}
	
	@Test
	public void testGetAll() {
		List<Line> lines = lineService.getAll();
		assertEquals(3, lines.size());
	}

	@Test
	@Transactional
	public void testFindByName_found() {
		List<Line> lines = lineService.getAllByName("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA");

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
		List<Line> lines = lineService.getAllByName("asdfgdfsg");
		assertEquals(0, lines.size());
	}

	@Test
	@Transactional
	public void testFindByType_found() {
		List<Line> lines = lineService.getAllByTrafficType(TrafficType.BUS);

		assertEquals(2, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());

		lines = lineService.getAllByTrafficType(TrafficType.TRAM);

		assertEquals(1, lines.size());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", lines.get(0).getName());
		assertEquals("1T", lines.get(0).getMark());
		assertEquals(TrafficType.TRAM, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bulevar Jase Tomica", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	@Transactional
	public void testFindByZone_found() {
		List<Line> lines = lineService.getAllByTrafficZone(TrafficZone.FIRST);

		assertEquals(2, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	@Transactional
	public void testFindByMarkAndType_found() {
		Line line = lineService.getByMarkAndType("1A", TrafficType.BUS);

		assertNotNull(line);
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", line.getName());
		assertEquals("1A", line.getMark());
		assertEquals(TrafficType.BUS, line.getType());
		assertEquals(TrafficZone.FIRST, line.getZone());
		assertEquals(2, line.getStations().size());
		assertEquals("Bazar", line.getStations().get(0).getStation().getName());

		line = lineService.getByMarkAndType("1T", TrafficType.TRAM);

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
		Line line = lineService.getByMarkAndType("BB", TrafficType.BUS);
		assertNull(line);
	}

	@Test
	@Transactional
	public void testFindById_found() {
		Line line = lineService.getById(1L);

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
		Line line = lineService.getById(10L);
		assertNull(line);
	}

	@Test
	@Transactional
	public void testGetByStation_found() {
		List<Line> lines = lineService.getAllByStation(1L);

		assertEquals(2, lines.size());
		assertEquals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA", lines.get(0).getName());
		assertEquals("1A", lines.get(0).getMark());
		assertEquals(TrafficType.BUS, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bazar", lines.get(0).getStations().get(0).getStation().getName());

		lines = lineService.getAllByStation(7L);

		assertEquals(1, lines.size());
		assertEquals("BULEVAR J.T. - BAZAR - BULEVAR J.T.", lines.get(0).getName());
		assertEquals("1T", lines.get(0).getMark());
		assertEquals(TrafficType.TRAM, lines.get(0).getType());
		assertEquals(TrafficZone.FIRST, lines.get(0).getZone());
		assertEquals(2, lines.get(0).getStations().size());
		assertEquals("Bulevar Jase Tomica", lines.get(0).getStations().get(0).getStation().getName());
	}

	@Test
	@Transactional
	public void testGetByStation_notFound() {
		List<Line> lines = lineService.getAllByStation(10L);

		assertEquals(0, lines.size());
	}

}
