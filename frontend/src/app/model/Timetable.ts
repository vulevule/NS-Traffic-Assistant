import { Time } from '@angular/common';

export interface TimetableItemInterface{
    id? : number;
    line_id : number;
    timetable_id : number;
    type : String;
    startTime : Time[];
}