import { Component, OnInit } from '@angular/core';
import { LineDTO } from 'src/app/model/LineDTO';
import { LineService } from 'src/app/services/lines/line.service';
import { TimetableDtoInterface, CreateTimetableItemInterface } from 'src/app/model/Timetable';
import { TimetableService } from 'src/app/services/timetable/timetable.service';

@Component({
  selector: 'app-create-timetable',
  templateUrl: './create-timetable.component.html',
  styleUrls: ['./create-timetable.component.css', '../general.scss']
})
export class CreateTimetableComponent implements OnInit {

  lines : LineDTO[];
  timetable : TimetableDtoInterface;
  items : CreateTimetableItemInterface[];

  message : String = '';
  infoType : String;

  constructor(private lineService : LineService, private timetableService : TimetableService) { }

  ngOnInit() {
    this.items = [];
    this.lineService.getAll()
    .subscribe(data => {
      this.lines = data;
      this.lines.forEach(element => {
        let i : CreateTimetableItemInterface= {
          line_mark : element.mark,
          line_name : element.name,
          workdayTimes : '',
          sundayTimes : '',
          saturdayTimes : ''
        }
        this.items.push(i);
      });
    })
  }

  save(){
    this.timetable = {
      timetables : this.items
    }

    this.timetableService.createTimetable(this.timetable)
    .subscribe(
      data => {
        this.message = data;
        this.infoType = 'success';
      },
      error => {
        this.message = error.error;
        this.infoType = 'danger';
      }
    )
  }

}
