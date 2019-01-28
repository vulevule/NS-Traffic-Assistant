import { Component, OnInit, Input } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { first, debounceTime, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css', './general.scss']
})
export class SearchFormComponent implements OnInit {

  tickets: TicketInterface[];
  @Input() role : String;

  //selectSNO : any ;

  displayType = {
    bus : true,
    metro : false,
    tram : false
  }

  displayZone = {
    first : true,
    second : false
  }

  displayTime = {
    annual : true, 
    month : false, 
    daily : false, 
    single : false
  }

  constructor(private ticketService: TicketServiceService) { }


  // search = (text$: Observable<String>) =>
  //   text$.pipe(
  //     debounceTime(200),
  //     map(term => term === '' ? []
  //       : this.tickets.filter(v => v.serialNo.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0.10)
  //     )
  //   )

  // formatter = (x: { serialNo: String }) => x.serialNo;

  ngOnInit() {
    this.getTickets();
  }

  getTickets(){
    if(this.role === 'INSPECTOR'){
      this.ticketService.getAllTickets()
      .subscribe(data => {
        this.tickets = data;
      }, error => {
        alert (error.error);
      })
    }else if (this.role === 'PASSENGER'){
      this.ticketService.getMyTicket()
      .subscribe(data => {
        this.tickets = data;
      }, error => {
        alert(error.error);
      })
     
    }
  }
}

