package com.team9.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.TrafficType;
import com.team9.repository.StationRepository;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	private StationRepository stationRepository;
	
	@Override
	public Station createStation(Station s) {
		Station find = stationRepository.findByNameAndType(s.getName(), s.getType());
		if (find == null) {
			return stationRepository.save(s);
		}
		
		return null;
	}

	@Override
	public boolean deleteStation(Long id) {
		Optional<Station> find = stationRepository.findById(id);
		if (!find.isPresent()) {
			return false;
		}
		
		stationRepository.delete(find.get());
		return true;
	}

	@Override
	public Station updateStation(Station s) {
		Optional<Station> find = stationRepository.findById(s.getId());
		if (!find.isPresent()) {
			return null;
		}
		
		return stationRepository.save(s);		
	}

	@Override
	public Collection<Station> getAllByType(TrafficType t) {
		return stationRepository.findByType(t);
	}

	@Override
	public Collection<Station> getByName(String name) {
		return stationRepository.findByName(name);
	}

	@Override
	public Station getById(Long id) {
		return stationRepository.findById(id).get();
	}

	@Override
	public Collection<Station> getAllByLine(Line l) {
		ArrayList<Station> retVal = new ArrayList<Station>();
		ArrayList<Station> list = (ArrayList<Station>) stationRepository.findAll();
		
		for (Station s : list) {
			if (s.getLines().contains(l)) {
				retVal.add(s);
			}
		}
		
		return retVal;
		
	}

	@Override
	public Collection<Station> getAll() {
		return (Collection<Station>) stationRepository.findAll();
	}

}
