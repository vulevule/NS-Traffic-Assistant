package com.team9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.StationDTO;
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
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public Station createStation(StationDTO s) throws StationAlreadyExistsException {
		Station find = stationRepository.findByNameAndType(s.getName(), s.getType());
		if (find == null) {
			Station station = new Station(s.getName(), s.getType(), s.getxCoordinate(), s.getyCoordinate(), null, null);
			Address findAddress = addressRepository.findByStreetAndCityAndZip(s.getAddressName(), s.getAddressCity(), s.getAddressZip());
			if (findAddress == null) {
				findAddress = new Address(s.getAddressName(), s.getAddressCity(), s.getAddressZip(), null, null);
				addressRepository.save(findAddress);
			}
			station.setAddress(findAddress);
			
			return stationRepository.save(station);
		} else {
			throw new StationAlreadyExistsException();
		}		
	}

	@Override
	public boolean deleteStation(Long id) throws StationNotFoundException {
		Optional<Station> find = stationRepository.findById(id);
		if (!find.isPresent()) {
			throw new StationNotFoundException();
		} else {
			stationRepository.delete(find.get());
			return true;
		}
	}

	@Override
	public Station updateStation(StationDTO s) throws StationNotFoundException {
		Optional<Station> find = stationRepository.findById(s.getId());
		if (find.isPresent()) {
			Station station = find.get();
			station.setName(s.getName());
			station.setxCoordinate(s.getxCoordinate());
			station.setyCoordinate(s.getyCoordinate());
			station.getAddress().setStreet(s.getAddressName());
			station.getAddress().setCity(s.getAddressCity());
			station.getAddress().setZip(s.getAddressZip());
			
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

	@Override
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
