import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { debounceTime, map } from 'rxjs/operators';
import { LineDTO } from 'src/app/model/LineDTO';
import { LineService } from 'src/app/services/lines/line.service';
import { TimetableItemInterface } from 'src/app/model/Timetable';
import { TimetableService } from 'src/app/services/timetable/timetable.service';
import { Time } from '@angular/common';
@Component({
  selector: 'app-search-timetable',
  templateUrl: './search-timetable.component.html',
  styleUrls: ['./search-timetable.component.css', '../general.scss']
})
export class SearchTimetableComponent implements OnInit {


  @Input() role: String;

  timetableItem: TimetableItemInterface;
  lines: LineDTO[];
  selectLine: any;
  zone: String = 'FIRST';
  timetableType: String = 'WORKDAY';
  trafficType: String = 'BUS';
  message: String = '';
  infoType: String;
  times : Time[];

  search = (text$: Observable<String>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term === '' ? []
        : this.lines.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0.10)
      )
    )

  formatter = (x: { name: String }) => x.name;

  constructor(private lineService: LineService, private timetableService: TimetableService) {
    this.zone = 'FIRST';
    this.trafficType = 'BUS';
    this.timetableType = 'WORKDAY';
  }

  ngOnInit() {
    this.getLines();
  }


  getLines() {
    this.lineService.getAllByZoneAndTrafficType(this.zone, this.trafficType)
      .subscribe(
        data => {
          this.lines = data;
        },
        error => {
          this.message = error.error;
          this.infoType = 'warning';
        }
      )
  }

  view(){
    this.timetableService.getTimetableItemsByLineAndType(this.selectLine.id, this.timetableType)
      .subscribe(
        data => {
          this.timetableItem = data;
          this.times = this.timetableItem.startTime;
        }, 
        error => {
          this.message = 'error';
        }
      )
  }
}
