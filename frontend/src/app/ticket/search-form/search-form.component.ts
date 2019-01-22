import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css', './general.scss']
})
export class SearchFormComponent implements OnInit {

  tickets: Ticket[];
  page : number;
  numOfTicket : number;


  constructor(private ticketService: TicketServiceService) { }

  async ngOnInit() {
    this.page = 1;
    this.ticketService.getAll(this.page-1)
      .subscribe(data => {
        this.tickets = data;
      }, 
      error => {
        alert(error.message);
      }
      )

    this.ticketService.getNumOfTicket()
    .subscribe( data => {
      this.numOfTicket = data;
    }, 
      error => {
        alert(error.message);
      }
      )

  }

  async changePage(){
    this.ticketService.getAll(this.page-1)
      .subscribe(data => {
        this.tickets = data;
      }, error => {
        alert (error.message);
      })
  }
}

