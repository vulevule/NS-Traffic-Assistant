package com.team9.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.team9.dto.LineDto;
import com.team9.dto.LocationDto;
import com.team9.dto.StationDTO;
import com.team9.dto.StationLineDto;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.service.LineService;
import com.team9.service.LoadDataService;
import com.team9.service.StationService;
import com.team9.util.Stop;
import com.team9.util.UtilLine;
import com.team9.util.UtilLocation;

@RestController
public class LoadDataController {

	@Autowired
	private LineService lineService;

	@Autowired
	private StationService stationService;

	@Autowired
	private LoadDataService loadDataService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/loadData/{name}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> loadData(@PathVariable String name) {
		logger.info(">> Load data from file " + name);

		int statSize = 0;
		List<UtilLine> data;
		String message;
		try {
			data = loadDataService.getParsedDataFromFile(name);

			for (UtilLine line : data) {
				int type = (data.indexOf(line) + data.size()) % 3; // Upamtiti ovo! Ovako svaka sledeca linija ce da
																	// bude sledeci TrafficType, na isti nacin uraditi
																	// timeTable

				LineDto lineDTO = new LineDto();
				lineDTO.setMark(line.getName());
				lineDTO.setName(line.getDescription());
				lineDTO.setType(TrafficType.values()[type]);
				lineDTO.setZone(TrafficZone.FIRST);
				lineDTO.setStations(new ArrayList<StationLineDto>());
				lineDTO.setRoute(new ArrayList<LocationDto>());
				for (UtilLocation ul : line.getCoordinates()) {
					lineDTO.getRoute().add(new LocationDto(ul.getLat(), ul.getLon()));
				}

				for (Map.Entry<Stop, Double> entry : line.getStopsAndTimes().entrySet()) {
					Stop temp = entry.getKey();
					int index = (new ArrayList<>(line.getStopsAndTimes().keySet())).indexOf(temp);
					StationDTO statDto = new StationDTO(temp.getName(), TrafficType.values()[type], temp.getLon(),
							temp.getLat(), new ArrayList<StationLineDto>());

					Station created;
					StationLineDto sl;
					try {
						created = stationService.createStation(statDto);

					} catch (StationAlreadyExistsException e) {
						created = stationService.getByNameAndType(statDto.getName(), statDto.getType());

						// try {
						// Station found = stationService.getByNameAndType(statDto.getName(),
						// statDto.getType());
						// statDto.setId(found.getId());
						// created = stationService.updateStation(statDto);
						// sl = new StationLineDto(index, entry.getValue().intValue(), created.getId(),
						// 0L,
						// created.getName(), lineDTO.getName(), lineDTO.getMark());
						// lineDTO.getStations().add(sl);
						// statSize++;
						//
						// } catch (StationNotFoundException e1) {
						// message = "Station " + statDto.getName() + " not found";
						// return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
						// } catch (StationAlreadyExistsException e1) {
						// message = statDto.getType() + " station " + statDto.getName() + " already
						// exists!";
						// return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
						// }
					}

					sl = new StationLineDto(index, entry.getValue().intValue(), created.getId(), 0L, created.getName(),
							lineDTO.getName(), lineDTO.getMark());
					lineDTO.getStations().add(sl);
					statSize++;
				}

				// TODO Ovde dodati kreiranje polazaka, posto LineUtil ima u sebi timeTable, a
				// to je jedna lista od koje se na slican nacin kao za nasumican odabir
				// TrafficType
				// linije moze lako napraviti tri liste za radni dan, subotu i nedelju

				try {
					lineService.createLine(lineDTO);
				} catch (LineAlreadyExistsException e) {
					message = lineDTO.getType() + " line " + lineDTO.getMark() + " already exists!";
					return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
				} catch (StationNotFoundException e) {
					message = e.getMessage();
					return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
				}

			}

		} catch (IOException e) {
			return new ResponseEntity<>("Input file not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
		}

		message = "Loaded " + data.size() + " lines and " + statSize + " stations";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

}
