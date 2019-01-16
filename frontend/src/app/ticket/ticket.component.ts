import { Component, OnInit } from '@angular/core';
import { Ticket, BuyTicket } from '../model/Ticket';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css', './general.scss']
})
export class TicketComponent implements OnInit {

  myTickets : Ticket[];
  buyTicket : BuyTicket;
  

  displayType = {
    bus : true,
    tram : true,
    metro : true
  }

  displayZone = {
    first : true,
    second : true
  }

  displayTime ={
    annual : true, 
    month : true, 
    daily : true, 
    single : true
  }

  constructor() { }

  async ngOnInit() {
    this.myTickets = [];
    this.buyTicket.price = 0;
  }

}
