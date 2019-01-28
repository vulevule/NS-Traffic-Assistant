import { Component, OnInit, Input } from '@angular/core';
import { Time } from '@angular/common';
import { TimetableItemInterface } from 'src/app/model/Timetable';

@Component({
  selector: 'app-display-timetable',
  templateUrl: './display-timetable.component.html',
  styleUrls: ['./display-timetable.component.css', '../general.scss']
})
export class DisplayTimetableComponent implements OnInit {

  @Input() displayItems: { mark: String, name: String, times: Time[] }[];


  constructor() { }

  ngOnInit() {

  }




}
