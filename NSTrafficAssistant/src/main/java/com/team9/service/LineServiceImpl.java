package com.team9.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.LineDto;
import com.team9.dto.StationLineDto;
import com.team9.exceptions.StationNotFoundException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.model.StationLine;
import com.team9.model.TimetableItem;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;
import com.team9.repository.StationRepository;

@Service
public class LineServiceImpl implements LineService{
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private StationRepository stationRepository;

	@Override
	public boolean createLine(Line l) {
		lineRepository.save(l);
		return true;
	}

	@Override
	public Line updateLine(Line l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteLine(Line l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Line getByName(String name) {
		return lineRepository.findByName(name);
	}

	@Override
	public Collection<Line> getAllByTrafficType(TrafficType tt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Line> getAllByTrafficZone(TrafficZone tz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Line> getAllByStation(Station s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Line> getAllByTimetableItem(TimetableItem tti) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Line LineDtoToLine(LineDto ldto) throws WrongTrafficTypeException, WrongTrafficZoneException, StationNotFoundException {
		Line l = new Line();
		
		l.setName(ldto.getName());
		
		if(ldto.getTrafficType().toUpperCase().equals("BUS")) {
			l.setType(TrafficType.BUS);
		}
		else if(ldto.getTrafficType().toUpperCase().equals("METRO")){
			l.setType(TrafficType.METRO);
		}
		else if(ldto.getTrafficType().toUpperCase().equals("TRAM")) {
			l.setType(TrafficType.TRAM);
		}
		else {
			throw new WrongTrafficTypeException("Wrong traffic type chosen!");
		}
		
		if(ldto.getTrafficZone().toUpperCase().equals("FIRST")) {
			l.setZone(TrafficZone.FIRST);
		}
		else if(ldto.getTrafficZone().toUpperCase().equals("SECOND")) {
			l.setZone(TrafficZone.SECOND);
		}
		else {
			throw new WrongTrafficZoneException("Wrong traffic zone chosen!");
		}
		
		/*treba jos uvezati timetable item-e i station-e sa linijom*/
		Collection<StationLineDto> stationsInLine = ldto.getStations();
		
		/*Proveravamo da li sve stanice kroz koje prolazi nova linija postoje u sistemu,
		 * ako postoje uvezujemo ih sa linijom u suprotnom prijavljuje se greska*/
		for (StationLineDto station : stationsInLine) {
			Station s = stationRepository.findByNameAndType(station.getStation(), l.getType());
			if(s != null) {
				l.getStations().add(new StationLine(station.getStationNum(), station.getArrival(), s, l));
			}
			else {
				throw new StationNotFoundException("Station "+station.getStation()+" doesn't exist!");
			}
		}
		
		/*Kolekcija sa timetable item-ima ostaje prazna pri kreiranju linije, dodavanje polazaka je
		 * pozebna aktivnost administratora.*/
		
		l.setInUse(false);//linija jos nije aktivna posto nema polazaka, kada se linija brise ovo polje takodje ima vrednost false
		
		return l;
	}

}
