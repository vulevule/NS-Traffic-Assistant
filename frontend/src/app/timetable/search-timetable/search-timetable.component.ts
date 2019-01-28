import { Component, OnInit } from '@angular/core';
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


  allTimetables = []; //dobavimo sve timetable-ove po zoni i tipu prevoza 

  items : {mark : String, name : String, times : Time[] }[];

  selected = []; //ovo su svi izabrani rasporedi koje treba prikazati 
  dropdownSettings = {};

  displayItems : TimetableItemInterface[];

  zone: String = 'FIRST';
  trafficType: String = 'BUS'
  timetableType: String;



  message: String = '';
  infoType: String;


  constructor(private lineService: LineService, private timetableService: TimetableService) {
    this.timetableType = 'WORKDAY';
  }



  ngOnInit() {

    this.getAllTimetablesByTypeAndZone();

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'line_mark',
      textField: 'line_name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };

  }

  getAllTimetablesByTypeAndZone(){
    this.timetableService.getAllTimetableByZoneAndTrafficType(this.zone, this.trafficType)
      .subscribe(
        data => {
          this.allTimetables = data;
        },
        error => {
          if(error.status === 400){
            this.message = 'Invalid traffic zone!';
            this.infoType = 'danger';
          }else if(error.status === 409){
            this.message = "Invalid traffic type!"
            this.infoType = 'danger';
          }else if(error.status === 404){
            this.message = "There is no active timetable!"
            this.infoType = 'danger';
          }
        }
      )
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }


 view(){
  
   this.displayItems = [];


   this.selected.forEach(s => {
     this.allTimetables.forEach(i => {
       if(s.line_mark === i.line_mark){
         this.displayItems.push(i); //izdvojili smo sve iteme koji treba da se prikazu
       }
     });
     
   });
   this.makeDisplayItems();
  
 }

 makeDisplayItems(){
  //ovako, prodjemo kroz sve iteme-e i napravimo
  this.items = [];
  this.displayItems.forEach(element => {
    if(this.timetableType === 'WORKDAY'){
      if(element.workdayTimes.length !== 0){
      let d_times: Time[] = this.makeDisplayString(element.workdayTimes);
      this.items.push({mark : element.line_mark, name : element.line_name, times : d_times});
      }else{
        this.message = 'The line ' + element.line_mark + ' ' + element.line_name+' does not have a timetable for working days'
        this.infoType = 'info';
      }
    }else if(this.timetableType === 'SUNDAY'){
      
      let s_times: Time[] = this.makeDisplayString(element.sundayTimes);
      this.items.push({mark : element.line_mark, name : element.line_name, times :s_times});
      
    }else if(this.timetableType === 'SATURDAY'){
      
      let sa_times: Time[] = this.makeDisplayString(element.saturdayTimes);
      this.items.push({mark : element.line_mark, name : element.line_name, times : sa_times});
      
    }
  });
}


makeDisplayString(times : Time[]) : Time[]{
  var result = [] ;

  while(times.length > 0){
    let m = times.splice(0, 4);
    result.push(m);

  }
  
  return result;
}

}
