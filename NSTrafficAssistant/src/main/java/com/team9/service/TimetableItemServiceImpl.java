package com.team9.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.model.Line;
import com.team9.model.TimeTableType;
import com.team9.model.Timetable;
import com.team9.model.TimetableItem;
import com.team9.repository.TimeTableItemRepository;

@Service
public class TimetableItemServiceImpl  implements TimetableItemService{

	
	@Autowired 
	private TimeTableItemRepository timetableItemRepo;
	
	@Override
	public List<TimetableItem> addItems(Line foundLine, List<Time> workdayTimes, List<Time> saturdayTimes,
			List<Time> sundayTimes, Timetable saveTimetable) {
		// TODO Auto-generated method stub
		
		//sad treba od svake liste da napravimo listu itema i da ih spakujemo u jednu zajednicku listu
		List<TimetableItem> wokrdayItems = convertToItems(foundLine, workdayTimes, TimeTableType.WORKDAY, saveTimetable);
		List<TimetableItem> saturdayItems = convertToItems(foundLine, saturdayTimes, TimeTableType.SATURDAY, saveTimetable);
		List<TimetableItem> sundayItems = convertToItems(foundLine, sundayTimes, TimeTableType.SUNDAY, saveTimetable);
		
		List<TimetableItem> allItems = new ArrayList<>();
		allItems.addAll(sundayItems); allItems.addAll(saturdayItems); allItems.addAll(wokrdayItems);
		
		allItems = this.timetableItemRepo.saveAll(allItems);
		
		return allItems;
	}

	private List<TimetableItem> convertToItems(Line foundLine, List<Time> times, TimeTableType type,
			Timetable timetable) {
		// TODO Auto-generated method stub
		List<TimetableItem> items = new ArrayList<>();
		for(Time t : times){
			TimetableItem ti  = new TimetableItem(t, type, foundLine, timetable);
			items.add(ti);
		}
		
		return items;
	}
	
	

}
