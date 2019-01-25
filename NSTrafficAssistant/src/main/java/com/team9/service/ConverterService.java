package com.team9.service;

import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTimeTableTypeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
import com.team9.model.TimeTableType;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;

public class ConverterService {

	/*
	 * klasa sluzi za konvertovanje stringova u enume i baca izuzetak ako string
	 * ne odgovara ni jednom enumu
	 */

	public static TrafficType convertStringToTrafficType(String type) throws WrongTrafficTypeException {
		if (type.toUpperCase().equals(TrafficType.BUS.name())) {
			return TrafficType.BUS;
		} else if (type.toUpperCase().equals(TrafficType.METRO.name())) {
			return TrafficType.METRO;
		} else if (type.toUpperCase().equals(TrafficType.TRAM.name())) {
			return TrafficType.TRAM;
		} else {
			throw new WrongTrafficTypeException("Incorect traffic type: " + type);
		}
	}

	public static TrafficZone convertStringToZone(String zone) throws WrongTrafficZoneException {
		if (zone.toUpperCase().equals(TrafficZone.FIRST.name())) {
			return TrafficZone.FIRST;
		} else if (zone.toUpperCase().equals(TrafficZone.SECOND.name())) {
			return TrafficZone.SECOND;
		} else {
			throw new WrongTrafficZoneException("Incorect traffic zone: " + zone);
		}
	}

	public static TimeTicketType convertStringToTimeTicketType(String time) throws WrongTicketTimeException {
		if (time.toUpperCase().equals(TimeTicketType.ANNUAL.name())) {
			return TimeTicketType.ANNUAL;
		} else if (time.toUpperCase().equals(TimeTicketType.DAILY.name())) {
			return TimeTicketType.DAILY;
		} else if (time.toUpperCase().equals(TimeTicketType.MONTH.name())) {
			return TimeTicketType.MONTH;
		} else if (time.toUpperCase().equals(TimeTicketType.SINGLE.name())) {
			return TimeTicketType.SINGLE;
		} else {
			throw new WrongTicketTimeException("Incorect time ticket: " + time);
		}
	}

	public static UserTicketType convertStringToUserTicketType(String user) throws WrongUserTicketTypeException {
		if (user.toUpperCase().equals(UserTicketType.STUDENT.name())) {
			return UserTicketType.STUDENT;
		} else if (user.toUpperCase().equals(UserTicketType.HANDYCAP.name())) {
			return UserTicketType.HANDYCAP;
		} else if (user.toUpperCase().equals(UserTicketType.REGULAR.name())) {
			return UserTicketType.REGULAR;
		} else if (user.toUpperCase().equals(UserTicketType.SENIOR.name())) {
			return UserTicketType.SENIOR;
		} else {
			throw new WrongUserTicketTypeException("Incorect user ticket type: " + user);
		}
	}

	public static TimeTableType convertStringToTimeTableType(String type) throws WrongTimeTableTypeException {
		if (type.toUpperCase().equals(TimeTableType.SATURDAY.name())) {
			return TimeTableType.SATURDAY;
		} else if (type.toUpperCase().equals(TimeTableType.SUNDAY.name())) {
			return TimeTableType.SUNDAY;
		} else if (type.toUpperCase().equals(TimeTableType.WORKDAY.name())) {
			return TimeTableType.WORKDAY;
		} else {
			throw new WrongTimeTableTypeException("Incorect time table type: " + type);
		}
	}
}
