import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Subject, Observable, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import {  ReportInterface } from 'src/app/model/Report';

const months : {id: number, name : String}[]= [
  {id : 1, name : 'January'},
  {id : 2, name : 'February'},
  {id : 3, name:  'March'},
  {id : 4, name : 'April'},
  {id : 5, name :  'May'},
  {id : 6, name :  'June'},
  {id : 7, name :  'Jule'},
  {id : 8, name :  'August'},
  {id : 9, name : 'September'},
  {id : 10, name :  'October'},
  {id : 11, name : 'November'},
  {id : 12, name :  'December'}];

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css', './general.scss']
})
export class ReportComponent implements OnInit {

  report: ReportInterface;

  public type: string = 'MONTH';

  year: number = 2019;

  model: any;

  message: string = '';

  errorType : string = 'success'; //da bi znala kako da ispisem poruku

  @ViewChild('instance') instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  search = (text$: Observable<String>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term === '' ? []
        : months.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0.10)
      )
    )

  formatter = (x: { name: String }) => x.name;


  constructor(private ticketService: TicketServiceService) { }

  ngOnInit() {
  }

  getReport() {
    this.message = '';
    //treba proveriti i godinu koja je unsena da li je u odgovarajucem formatu 
    if (this.year > 2019 || this.year < 2000) {
      this.report = undefined;
      this.message = "The year must be between 2000 and 2019!";
      this.errorType = 'warning';
    }else if (this.type === 'MONTH' && this.model === undefined){
      this.report = undefined;
      this.message = "The month must be selected!"
      this.errorType = 'warning';
    }
    else {
      var id : number;
      if(this.type !== 'MONTH'){
        id = 1;
      }else{
        id = this.model.id;
        
      }
      this.ticketService.getReport(id, this.year, this.type )
        .subscribe(
          data => {
            this.report = data;
            if (this.report.money === 0){
              this.message = 'There is no report for the requested period! '
              this.errorType = 'info';
            }else{
              this.message = '';
              this.errorType = 'success';
            }
          }, 
          error =>{
            if(error.error === ''){
              this.message = 'Invalid month or year';
            }else {
              this.message = error.error;
            }
           
            this.errorType = 'danger';
          }
        )
    }
  }

}
