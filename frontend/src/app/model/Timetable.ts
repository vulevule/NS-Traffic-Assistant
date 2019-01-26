import { Time } from '@angular/common';

export interface TimetableItemInterface{
    id? : number;
    line_name : string;
    line_mark : string;
    timetable_id : number;
    sundayTimes : Time[];
    saturdayTimes : Time[];
    workdayTimes : Time[];
}


export interface CreateTimetableItemInterface{
    line_mark : String;
	line_name : String;
	workdayTimes : String;
	sundayTimes : String;
	saturdayTimes : String;
}

export interface TimetableDtoInterface{
    timetables : CreateTimetableItemInterface[];
}