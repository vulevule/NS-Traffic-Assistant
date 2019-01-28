package com.team9.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.LineDto;
import com.team9.dto.LocationDto;
import com.team9.dto.StationLineDto;
import com.team9.exceptions.InvalidInputFormatException;
import com.team9.exceptions.LineAlreadyExistsException;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.StationNotFoundException;
import com.team9.model.Line;
import com.team9.model.Location;
import com.team9.model.Station;
import com.team9.model.StationLine;
import com.team9.model.Timetable;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;
import com.team9.repository.LocationRepository;
import com.team9.repository.StationLineRepository;
import com.team9.repository.StationRepository;
import com.team9.repository.TimeTableRepository;

@Service
public class LineServiceImpl implements LineService {

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private StationLineRepository stationLineRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Line createLine(LineDto ldto)
			throws LineAlreadyExistsException, StationNotFoundException, InvalidInputFormatException {
		if (ldto.getName().length() < 2 || ldto.getMark() == "" || ldto.getType() == null || ldto.getZone() == null
				|| ldto.getRoute().size() < 2 || ldto.getStations().size() < 2) {
			throw new InvalidInputFormatException("Invalid input format!");
		}

		Line find = lineRepository.findByMarkAndType(ldto.getMark(), ldto.getType());
		if (find == null) {
			Line line = new Line(ldto.getMark(), ldto.getName(), ldto.getType(), ldto.getZone(), null, null);

			line.setRoute(new ArrayList<Location>());
			for (LocationDto loc : ldto.getRoute()) {
				Location newLoc = new Location(loc.getLat(), loc.getLon());

				if (line.getRoute().stream()
						.filter(o -> o.getLat().equals(newLoc.getLat()) && o.getLon().equals(newLoc.getLon()))
						.findFirst().isPresent()) {
					throw new InvalidInputFormatException("Line can not contain same coordinates in route!");
				}

				line.getRoute().add(newLoc);
			}

			line.setStations(new ArrayList<StationLine>());
			for (StationLineDto sl : ldto.getStations()) {
				Station foundStation = stationRepository.findById(sl.getStationId()).orElse(null);

				if (foundStation == null) {
					throw new StationNotFoundException("Station " + sl.getStationName() + " not found!");
				}
				if (foundStation.getType() != line.getType()) {
					throw new InvalidInputFormatException(foundStation.getType() + " station " + foundStation.getName()
							+ " can not be assigned to " + line.getType() + " line!");
				}

				StationLine newSl = new StationLine(sl.getStationNum(), sl.getArrival(), foundStation, line);
				line.getStations().add(newSl);
			}

//			lineRepository.save(line);
//
//			for (StationLine sl : line.getStations()) {
//				stationLineRepository.save(sl);
//			}

			return lineRepository.save(line);

		} else {
			throw new LineAlreadyExistsException();
		}
	}

	@Override
	public Line updateLine(LineDto ldto) throws LineNotFoundException, StationNotFoundException,
			LineAlreadyExistsException, InvalidInputFormatException {
		if (ldto.getName().length() < 2 || ldto.getMark() == "" || ldto.getType() == null || ldto.getZone() == null
				|| ldto.getRoute().size() < 2 || ldto.getStations().size() < 2) {
			throw new InvalidInputFormatException("Invalid input format!");
		}

		Optional<Line> find = lineRepository.findById(ldto.getId());
		if (find.isPresent()) {
			Line line = find.get();
			if (line.getType() != ldto.getType()) {
				throw new InvalidInputFormatException("Line type can not be changed!");
			}

			List<StationLine> stationsToRemove = stationLineRepository.findByLine(line);

			line.setZone(ldto.getZone());
			line.setName(ldto.getName());

			Line temp = lineRepository.findByMarkAndType(ldto.getMark(), ldto.getType());
			if (temp != null && temp.getId() != line.getId()) {
				throw new LineAlreadyExistsException();
			}

			line.setMark(ldto.getMark());

			// for (Location loc : line.getRoute()) {
			// locationRepository.delete(loc);
			// }

			line.getRoute().clear();

			for (LocationDto loc : ldto.getRoute()) {
				Location newLoc = new Location(loc.getLat(), loc.getLon());

				if (line.getRoute().stream()
						.filter(o -> o.getLat().equals(newLoc.getLat()) && o.getLon().equals(newLoc.getLon()))
						.findFirst().isPresent()) {
					throw new InvalidInputFormatException("Line can not contain same coordinates in route!");
				}

				line.getRoute().add(newLoc);
			}

			for (StationLine sl : stationsToRemove) {
				StationLine found = stationLineRepository.findById(sl.getId()).orElse(null);
				if (found == null) {
					throw new StationNotFoundException("Station " + sl.getStation().getName() + " not found!");
				}

				Station s = found.getStation();
				Line l = found.getLine();

				s.getLines().remove(found);
				l.getStations().remove(found);

				stationRepository.save(s);
				lineRepository.save(l);

				// found.setLine(null);
				// found.setStation(null);
				// stationLineRepository.save(found);

				stationLineRepository.deleteById(found.getId());

				found = stationLineRepository.findById(found.getId()).orElse(null);
				if (found != null) {
					// throw new StationNotFoundException("Station should be null but it is not!");
				} else {
					// throw new StationNotFoundException("All fine");
				}
			}

			line.getStations().clear();
			for (StationLineDto sl : ldto.getStations()) {
				Station foundStation = stationRepository.findById(sl.getStationId()).orElse(null);

				if (foundStation == null) {
					throw new StationNotFoundException("Station " + sl.getStationName() + " not found!");
				}
				if (foundStation.getType() != line.getType()) {
					throw new InvalidInputFormatException(foundStation.getType() + " station " + foundStation.getName()
							+ " can not be assigned to " + line.getType() + " line!");
				}

				StationLine newSl = new StationLine(sl.getStationNum(), sl.getArrival(), foundStation, line);
				line.getStations().add(newSl);
			}

			return lineRepository.save(line);
		} else {
			throw new LineNotFoundException(ldto.getType() + " line " + ldto.getMark() + " not found!");
		}
	}

	@Override
	public boolean deleteLine(Long id) throws LineNotFoundException {
		Line line = lineRepository.findById(id).orElse(null);
		if (line == null) {
			throw new LineNotFoundException("Line not found!");
		} else {
			// line.getStations().clear();

			lineRepository.delete(line);

//			for (StationLine sl : line.getStations()) {
//
//				// Station s = sl.getStation();
//				// Line l = sl.getLine();
//
//				// s.getLines().remove(sl);
//				// l.getStations().remove(sl);
//
//				// stationRepository.save(s);
//				// lineRepository.save(l);
//
//				// found.setLine(null);
//				// found.setStation(null);
//				// stationLineRepository.save(found);
//
//				stationLineRepository.deleteById(sl.getId());
//			}
			
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
		for (Line iter : allLines) {
			List<StationLine> stations = iter.getStations();
			for (StationLine sl : stations) {
				if (sl.getStation().getId() == stationId) {
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
	public Line getByMarkAndType(String name, TrafficType type) {
		return lineRepository.findByMarkAndType(name, type);
	}

}
