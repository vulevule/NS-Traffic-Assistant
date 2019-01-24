import { Component, OnInit, Input } from '@angular/core';
import { TimetableItemInterface } from 'src/app/model/Timetable';
import { Time } from '@angular/common';

@Component({
  selector: 'app-display-timetable',
  templateUrl: './display-timetable.component.html',
  styleUrls: ['./display-timetable.component.css', '../general.scss']
})
export class DisplayTimetableComponent implements OnInit {

  @Input() role : String;
  @Input() times : Time[];
  @Input() line : String;
  

  timetableItem : TimetableItemInterface;
  
  constructor() { }

  ngOnInit() {
  }

}
