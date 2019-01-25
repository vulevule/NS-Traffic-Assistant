import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Subject, Observable, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { Report } from 'src/app/model/Report';

const months = ['January', 'February', 'March', 'April', 'May', 'June', 'Jule', 'August', 'September',
  'October', 'November', 'December'];

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css', './general.scss']
})
export class ReportComponent implements OnInit {

  report: Report;

  public type: string = 'MONTH';

  year: number = 0;

  model: string;

  message: string = '';

  errorType : string = 'success'; //da bi znala kako da ispisem poruku

  @ViewChild('instance') instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? months : months.filter(m => m.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))

    )
  }


  constructor(private ticketService: TicketServiceService) { }

  ngOnInit() {
  }

  async getReport() {
    //treba proveriti i godinu koja je unsena da li je u odgovarajucem formatu 
    if (this.year > 2020 || this.year < 2000) {
      this.report = undefined;
      this.message = "The year must be between 2000 and 2020!";
      this.errorType = 'warning';
    }else if (this.type === 'MONTH' && this.model === ''){
      this.report = undefined;
      this.message = "The month must be selected!"
      this.errorType = 'warning';
    } 
    else {
      this.message = 'Success';
      this.errorType = 'success';
      // await this.ticketService.getReport(this.model, this.year, this.type)
      //   .then(data => {
      //     this.report = data
      //   },
      //     reason => {
      //       alert('ERROR');
      //     }
      //   )
      //test napravicemo jedan report i vratiti
      this.report = this.makeReport();
    }
  }

  makeReport() : Report{
    var r : Report = {
      numOfStudentMonthTicket : 2,
    numOfHandycapMonthTicket : 2,
    numOfSeniorMonthTicket : 1,
    numOfRegularMonthTicket : 0,

    // godisnje
    numOfStudentYearTicket: 2,
    numOfHandycapYearTicket: 3,
    numOfSeniorYearTicket: 0,
    numOfRegularYearTicket: 0,

    // single
    numOfStudentSingleTicket: 0,
    numOfHandycapSingleTicket: 0,
    numOfSeniorSingleTicket: 0,
    numOfRegularSingleTicket: 0,

    // dnevne
    numOfStudentDailyTicket: 0,
    numOfHandycapDailyTicket: 0,
    numOfSeniorDailyTicket: 0,
    numOfRegularDailyTicket: 0,

    // broj kupljenih karti za odredjeni prevoz
    numOfBusTicket: 1,
    numOfMetroTicket: 8,
    numOfTramTicket: 1,

    // zarada
    money: 1200,
    }
    return r;
  }

}
