package com.team9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.StationDTO;
import com.team9.exceptions.InvalidInputFormatException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationAlreadyExistsException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Address;
import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.StationLine;
import com.team9.model.TrafficType;
import com.team9.repository.AddressRepository;
import com.team9.repository.LineRepository;
import com.team9.repository.StationRepository;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	private StationRepository stationRepository;
	
	@Autowired
	private LineRepository lineRepository;
	
	@Override
	public Station createStation(StationDTO s) throws StationAlreadyExistsException, InvalidInputFormatException {
		if(s.getName() == "" || s.getName().length() < 2 || s.getType() == null || s.getxCoordinate() == 0 || s.getyCoordinate() == 0) {
			throw new InvalidInputFormatException();
		}
		
		Station find = stationRepository.findByNameAndType(s.getName(), s.getType());
		if (find == null) {
			Station station = new Station(s.getName(), s.getType(), s.getxCoordinate(), s.getyCoordinate(), null);
			station.setLines(new ArrayList<StationLine>());
			
			return stationRepository.save(station);
		} else {
			throw new StationAlreadyExistsException();
		}		
	}

	@Override
	public boolean deleteStation(Long id) throws StationNotFoundException, InvalidInputFormatException {
		Optional<Station> find = stationRepository.findById(id);
		if (!find.isPresent()) {
			throw new StationNotFoundException();
		} else {
			if(find.get().getLines().size() == 2) {
				throw new InvalidInputFormatException("Station can not be deleted now, check lines first!");
			}
			
			stationRepository.deleteById(find.get().getId());
			return true;
		}
	}

	@Override
	public Station updateStation(StationDTO s) throws StationNotFoundException, StationAlreadyExistsException, InvalidInputFormatException {
		if(s.getName() == "" || s.getName().length() < 2 || s.getType() == null || s.getxCoordinate() == 0 || s.getyCoordinate() == 0) {
			throw new InvalidInputFormatException();
		}
		
		Optional<Station> find = stationRepository.findById(s.getId());
		if (find.isPresent()) {
			Station station = find.get();
			
			if(station.getType() != s.getType()) {
				throw new InvalidInputFormatException();
			}
			
			Station temp = stationRepository.findByNameAndType(s.getName(), s.getType());
			if(temp != null && temp.getId() != station.getId()) {
				throw new StationAlreadyExistsException();
			}
			station.setName(s.getName());
			station.setxCoordinate(s.getxCoordinate());
			station.setyCoordinate(s.getyCoordinate());
			
			return stationRepository.save(station);
		} else {
			throw new StationNotFoundException();
		}		
	}

	@Override
	public List<Station> getAllByType(TrafficType t) {
		return stationRepository.findByType(t);
	}

	@Override
	public List<Station> getByName(String name) {
		return stationRepository.findByName(name);
	}
	
	@Override
	public List<Station> getByNameContains(String name) {
		return stationRepository.findByNameContains(name);
	}

	@Override
	public Station getById(Long id) {
		return stationRepository.findById(id).orElse(null);
	}

	public List<Station> getAllByLine(Long lineId) throws LineNotFoundException {
		Line line = lineRepository.findById(lineId).orElse(null);
		if (line != null) {
			List<StationLine> temp = line.getStations();
			List<Station> retVal = new ArrayList<Station>();
			
			for(StationLine iter : temp) {
				retVal.add(iter.getStation());


			}
			
			return retVal;
		} else {
			throw new LineNotFoundException();
		}
		
		
	}




	@Override
	public List<Station> getAll() {
		return stationRepository.findAll();
	}

	@Override
	public Station getByNameAndType(String name, TrafficType type) {
		return stationRepository.findByNameAndType(name, type);
	}

}
