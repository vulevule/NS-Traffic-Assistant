package com.team9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.LineDto;
import com.team9.dto.LocationDto;
import com.team9.dto.StationLineDto;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.model.Line;
import com.team9.model.Location;
import com.team9.model.Station;
import com.team9.model.StationLine;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;
import com.team9.repository.LocationRepository;
import com.team9.repository.StationLineRepository;
import com.team9.repository.StationRepository;
import com.team9.repository.TimeTableRepository;

@Service
public class LineServiceImpl implements LineService{
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private StationLineRepository stationLineRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TimeTableRepository timeTableRepository;
	
	@Override
	public Line createLine(LineDto ldto) throws LineAlreadyExistsException {
		Line find = lineRepository.findByNameAndType(ldto.getName(), ldto.getTrafficType());
		if (find == null) {
			Line line = new Line(ldto.getName(), ldto.getTrafficType(), ldto.getTrafficZone(), null, null, null);
			line.setTimeTable(timeTableRepository.findByActivate(true));
			
			line.setRoute(new ArrayList<Location>());
			for(LocationDto loc : ldto.getRoute()) {
				Location newLoc = new Location(loc.getLat(), loc.getLon());
				line.getRoute().add(newLoc);
			}
			
			line.setStations(new ArrayList<StationLine>());
			for(StationLineDto sl: ldto.getStations()) {
				Station foundStation = stationRepository.findById(sl.getStationId()).orElse(null);
				StationLine newSl = new StationLine(sl.getStationNum(), sl.getArrival(), foundStation, line);
				line.getStations().add(newSl);
			}
			
			return lineRepository.save(line);
		} else {
			throw new LineAlreadyExistsException();
		}	
	}

	@Override
	public Line updateLine(LineDto ldto) throws LineNotFoundException {
		Optional<Line> find = lineRepository.findById(ldto.getId());
		if (find.isPresent()) {
			Line line = find.get();
			line.setZone(ldto.getTrafficZone());	
			
			for(Location loc : line.getRoute()) {
				locationRepository.delete(loc);
			}
			line.getRoute().clear();
			for(LocationDto loc : ldto.getRoute()) {
				Location newLoc = new Location(loc.getLat(), loc.getLon());
				line.getRoute().add(newLoc);
			}
			
			for(StationLine sl : line.getStations()) {
				stationLineRepository.delete(sl);
			}
			line.getStations().clear();
			for(StationLineDto sl: ldto.getStations()) {
				Station foundStation = stationRepository.findById(sl.getStationId()).orElse(null);
				StationLine newSl = new StationLine(sl.getStationNum(), sl.getArrival(), foundStation, line);
				line.getStations().add(newSl);
			}
			
			return lineRepository.save(line);
		} else {
			throw new LineNotFoundException();
		}	
	}

	@Override
	public boolean deleteLine(Long id) throws LineNotFoundException {
		Optional<Line> find = lineRepository.findById(id);
		if (!find.isPresent()) {
			throw new LineNotFoundException();
		} else {
			lineRepository.delete(find.get());
			return true;
		}
	}

	@Override
	public List<Line> getAll() {
		return lineRepository.findAll();
	}

	@Override
	public List<Line> getAllByName(String name) {
		return lineRepository.findByName(name);
	}

	@Override
	public List<Line> getAllByTrafficType(TrafficType type) {
		return lineRepository.findByType(type);
	}

	@Override
	public List<Line> getAllByTrafficZone(TrafficZone zone) {
		return lineRepository.findByZone(zone);
	}

	@Override
	public List<Line> getAllByStation(Long stationId) {
		List<Line> retVal = new ArrayList<>();
		List<Line> allLines = lineRepository.findAll();
		for(Line iter : allLines) {
			List<StationLine> stations = iter.getStations();
			for(StationLine sl : stations) {
				if(sl.getStation().getId() == stationId) {
					retVal.add(iter);
				}
			}
		}		
		
		return retVal;
	}

	@Override
	public Line getById(Long id) {
		return lineRepository.findById(id).orElse(null);
	}

	@Override
	public Line getByNameAndType(String name, TrafficType type) {
		return lineRepository.findByNameAndType(name, type);
	}

	

	

}
