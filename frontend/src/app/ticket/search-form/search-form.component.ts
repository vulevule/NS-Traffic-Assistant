import { Component, OnInit, Input } from '@angular/core';
import { TicketInterface, TicketReaderInterface } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css', './general.scss']
})
export class SearchFormComponent implements OnInit {

  tickets: TicketInterface[];
  page : number ;
  numOfTicket : number;
  @Input() role : string;


  constructor(private ticketService: TicketServiceService) { }

  async ngOnInit() {
    this.page = 1;
    this.changePage();
  }

  async changePage(){
    if(this.role === 'INSPECTOR'){
      this.ticketService.getAllTickets(this.page)
      .subscribe(data => {
        this.tickets = data.tickets;
        this.numOfTicket = data.numOfTickets;
      }, error => {
        alert (error.error);
      })
    }else if (this.role === 'PASSENGER'){
      this.ticketService.getMyTicket(this.page)
      .subscribe(data => {
        this.tickets = data.tickets;
        this.numOfTicket = data.numOfTickets;
      }, error => {
        alert(error.error);
      })
     
    }

    
  }

}

