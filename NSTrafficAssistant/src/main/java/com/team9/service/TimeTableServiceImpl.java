package com.team9.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.TimeTableItemDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.WrongTimeTableTypeException;
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
public class TimeTableServiceImpl implements TimeTableService{

	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private TimeTableItemRepository timeTableItemRepository;
	
	@Autowired
	private TimeTableRepository timeTableRepository;
	

	@Override
	public Boolean editTimeTable(TimeTableItemDto items) throws WrongTimeTableTypeException, LineNotFoundException {
		// na osnovu linije, tipa i aktivnog cenovnika pronadjemo sve iteme od te linije i izbrisemo ih iz baze 
		/*Line l = this.lineRepository.findById(items.getLine_id()).orElseThrow(() -> new LineNotFoundException());
		//2. proverimo da li je tip dobar
		TimeTableType t = ConverterService.convertStringToTimeTableType(items.getType());
		//3. moramo pronaci i timetable koji je aktivan 
		Timetable timeTable = this.timeTableRepository.findByActivate(true);
		
		List<TimetableItem> search_items = this.timeTableItemRepository.findByLineAndTypeAndTimeTable(l, t, timeTable);
		
		//sad izbirsemo sve stare iteme
		for(TimetableItem i : search_items){
			this.timeTableItemRepository.deleteById(i.getId());
		}
		//sad konvertujemo dto u iteme
		List<TimetableItem> newItem = convertDtoToTimeTableItem(items.getStartTime(), l, t, timeTable);
		
		//i sad sacuvamo nove iteme
		List<TimetableItem> saveItems = this.timeTableItemRepository.saveAll(newItem);
		
		
		if(saveItems.size() == newItem.size()){
			return true;
		}else{
			return false;
		}*/
		return false;
	}

	private List<TimetableItem> convertDtoToTimeTableItem(ArrayList<Time> items, Line l, TimeTableType type, Timetable timeTable) {
		// TODO Auto-generated method stub
		List<TimetableItem> result = new ArrayList<TimetableItem>();
		for(Time t : items){
			TimetableItem i = new TimetableItem(t, type, l, timeTable);
			result.add(i);
		}
		return result;
	}

	@Override
	public Collection<TimeTableItemDto> getTimeTableItemByZoneAndType(String zone, String type) throws WrongTrafficZoneException, WrongTrafficTypeException {
		// TODO Auto-generated method stub
		//izvucemo sve linije na osnovu zone  i tipa prevoza
		TrafficZone z = ConverterService.convertStringToZone(zone);
		TrafficType t = ConverterService.convertStringToTrafficType(type);
		
		List<Line> lines = this.lineRepository.findByTypeAndZone(t, z);
		//sad prodjemo kroz sve linije i za svaku od njih izvucemo sve iteme
		//pronadjemo aktivan red voznje 
		Timetable activeTimetable = this.timeTableRepository.findByActivate(true);
		
		List<TimeTableItemDto> result = new ArrayList<>();
		for(Line l : lines){
			List<TimetableItem> items = this.timeTableItemRepository.findByLineAndTimeTable(l, activeTimetable);
			//sad treba da prodjemo kroz iteme i izdvojimo vremena onih koji su radni dan, subota i nedelja
			List<TimetableItem> workdayItems = items.stream().filter(i -> i.getType() == TimeTableType.WORKDAY).collect(Collectors.toList());
			List<TimetableItem> sundayItems = items.stream().filter(i -> i.getType() == TimeTableType.SUNDAY).collect(Collectors.toList());
			List<TimetableItem> saturdayItems = items.stream().filter(i -> i.getType() == TimeTableType.SATURDAY).collect(Collectors.toList());

			
			//prodjemo kroz listu i izdvojimo samo vremena
			List<Time> workdayTimeItems = workdayItems.stream().map(i -> i.getStartTime()).collect(Collectors.toList());
			List<Time> sundayTimeItems = sundayItems.stream().map(i -> i.getStartTime()).collect(Collectors.toList());
			List<Time> saturdayTimeItems = saturdayItems.stream().map(i -> i.getStartTime()).collect(Collectors.toList());

			//napravimo objekta koji vracamo 
			TimeTableItemDto t_dto = new TimeTableItemDto((ArrayList<Time>)sundayTimeItems, (ArrayList<Time>)saturdayTimeItems, (ArrayList<Time>)workdayTimeItems, l.getId(), l.getName(), activeTimetable.getId());
			result.add(t_dto);			
		}
		
		
		return result;
	}
	
}
