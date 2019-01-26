package com.team9.service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9.dto.TimetableDto;
import com.team9.dto.TimetableItemCreateDto;
import com.team9.dto.TimetableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivateTimetable;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Line;
import com.team9.model.TimeTableType;
import com.team9.model.Timetable;
import com.team9.model.TimetableItem;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.LineRepository;
import com.team9.repository.TimeTableItemRepository;
import com.team9.repository.TimeTableRepository;

@Service

public class TimetableServiceImpl implements TimetableService {

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private TimeTableItemRepository timetableItemRepository;

	@Autowired
	private TimeTableRepository timetableRepository;

	@Autowired
	private TimetableItemService timetableItemService;

	@Override
	public Collection<TimetableItemDto> getTimeTableItemByZoneAndType(String zone, String type)
			throws WrongTrafficZoneException, WrongTrafficTypeException, NotFoundActivateTimetable {
		// TODO Auto-generated method stub
		// izvucemo sve linije na osnovu zone i tipa prevoza
		TrafficZone z = ConverterService.convertStringToZone(zone);
		TrafficType t = ConverterService.convertStringToTrafficType(type);

		List<Line> lines = this.lineRepository.findByTypeAndZone(t, z);
		// sad prodjemo kroz sve linije i za svaku od njih izvucemo sve iteme
		// pronadjemo aktivan red voznje
		Timetable activeTimetable = this.timetableRepository.findByActivate(true)
				.orElseThrow(() -> new NotFoundActivateTimetable("There is no active timetable!"));

		List<TimetableItemDto> result = new ArrayList<>();
		for (Line l : lines) {
			List<TimetableItem> items = this.timetableItemRepository.findByLineAndTimeTable(l, activeTimetable);
			// sad treba da prodjemo kroz iteme i izdvojimo vremena onih koji su
			// radni dan, subota i nedelja
			List<TimetableItem> workdayItems = items.stream().filter(i -> i.getType() == TimeTableType.WORKDAY)
					.collect(Collectors.toList());
			List<TimetableItem> sundayItems = items.stream().filter(i -> i.getType() == TimeTableType.SUNDAY)
					.collect(Collectors.toList());
			List<TimetableItem> saturdayItems = items.stream().filter(i -> i.getType() == TimeTableType.SATURDAY)
					.collect(Collectors.toList());

			// prodjemo kroz listu i izdvojimo samo vremena
			List<Time> workdayTimeItems = workdayItems.stream().map(i -> i.getStartTime()).collect(Collectors.toList());
			Collections.sort(workdayTimeItems);
			List<Time> sundayTimeItems = sundayItems.stream().map(i -> i.getStartTime()).collect(Collectors.toList());
			Collections.sort(sundayTimeItems);

			List<Time> saturdayTimeItems = saturdayItems.stream().map(i -> i.getStartTime())
					.collect(Collectors.toList());
			Collections.sort(saturdayTimeItems);

			// napravimo objekta koji vracamo
			TimetableItemDto t_dto = new TimetableItemDto((ArrayList<Time>) sundayTimeItems,
					(ArrayList<Time>) saturdayTimeItems, (ArrayList<Time>) workdayTimeItems, l.getId(), l.getName(),
					l.getMark(), activeTimetable.getId());
			result.add(t_dto);
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addTimetable(TimetableDto newTimetable) throws LineNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Timetable t = this.timetableRepository.findByActivate(true).orElse(null);
		if (t != null) {
			// ako nije null, setujemo mu vreme
			t.setExpirationDate(new java.sql.Date(new java.util.Date().getTime()));
			// sacuvamo ga u bazi
			this.timetableRepository.save(t);
		}
		
		// moramo proci kroz listu svih i jedan po jedan dodavati, a pre toga
		// napraviti jedan timetable i sacuvati ga

		// sacuvamo timetable
		Timetable saveTimetable = new Timetable(null, new java.sql.Date(new java.util.Date().getTime()), true);

		saveTimetable = this.timetableRepository.save(saveTimetable);

		// i sad nam jos ostaje da sacuvamo stavke cenovnika
		

		List<TimetableItem> allItems = new ArrayList<>();
		
		for(TimetableItemCreateDto i_dto : newTimetable.getTimetables()){
			Line foundLine = this.lineRepository.findByNameAndMark( i_dto.getLine_name(), i_dto.getLine_mark())
					.orElseThrow(() -> new LineNotFoundException(
							"Line with mark: " + i_dto.getLine_mark() + " does not exist!"));

			//iz parsiramo sva vremena
			List<Time> workdayTimes = parseAndConvertString(i_dto.getWorkdayTimes());
			List<Time> saturdayTimes = parseAndConvertString(i_dto.getSaturdayTimes());
			List<Time> sundayTimes = parseAndConvertString(i_dto.getSundayTimes());
			//pozovemo funkciju za cuvanje iz item servisa, ona nam vraca listu itema za svaku liniju
			List<TimetableItem> items = this.timetableItemService.addItems(foundLine, workdayTimes, saturdayTimes, sundayTimes, saveTimetable);
			allItems.addAll(items);
		}
		
		
		return true;
	}

	private List<Time> parseAndConvertString(String times) throws ParseException {
		// TODO Auto-generated method stub
		// 1. string isparsiramo po zarezu

		List<String> stringTimes = Stream.of(times.split(",")).map(String::trim).collect(Collectors.toList());
		
		DateFormat formatter = new SimpleDateFormat("HH:mm");

		
		for(String s : stringTimes)
		{
			System.out.println(s);
		}
		// jos da prodjemo kroz listu stringova i da ih pretvorimo u vreme
		List<Time> result = new ArrayList<Time>();
		for (String s : stringTimes) {
			result.add(new java.sql.Time(formatter.parse(s).getTime()));
		}

		return result;
	}

}
