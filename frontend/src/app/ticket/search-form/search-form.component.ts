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
  @Input() role: String;

  //selectSNO : any ;

  displayType = {
    bus: false,
    metro: false,
    tram: false
  }

  displayZone = {
    first: false,
    second: false
  }

  displayTime = {
    annual: false,
    month: false,
    daily: false,
    single: false
  }

  constructor(private ticketService: TicketServiceService) { }



  ngOnInit() {
    this.getTickets();
  }

  getTickets() {
    if (this.role === 'INSPECTOR') {
      this.ticketService.getAllTickets()
        .subscribe(data => {
          this.tickets = data;
        }, error => {
          alert(error.error);
        })
    } else if (this.role === 'PASSENGER') {
      this.ticketService.getMyTicket()
        .subscribe(data => {
          this.tickets = data;
        }, error => {
          alert(error.error);
        })

    }
  }
}

