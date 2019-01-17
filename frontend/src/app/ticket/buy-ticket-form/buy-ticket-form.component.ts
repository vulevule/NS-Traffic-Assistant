import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BuyTicket, Ticket } from 'src/app/model/Ticket';

@Component({
  selector: 'app-buy-ticket-form',
  templateUrl: './buy-ticket-form.component.html',
  styleUrls: ['./buy-ticket-form.component.css', './general.scss']
})
export class BuyTicketFormComponent implements OnInit {

  public buyTicket: BuyTicket;
  ticket: Ticket;
  message : String;



  constructor() { }

  async ngOnInit() {
    this.buyTicket = {
      price: 0,
      trafficType: 'BUS',
      trafficZone: 'FIRST',
      ticketTime: 'ANNUAL',
    }
    this.message = '';
  }


  async getPrice() {
    //ovde cemo pozivati funkciju sa back-a za dobavljanje cene 
    this.buyTicket.price = 98878;
  }


  async buy() {
    //pozvatu metodu iz servisa za kupovinu karata

    //dobijenu kartu prosledimo pretku da bi je prikazao 
    this.ticket = {
      id: 12,
      trafficType: 'BUS',
      trafficZone: 'FIRST',
      ticketTime: 'ANNUAL',
      price: 12000,
      userTicketType: 'STUDENT',
      serialNo: 'JBHGHAGHD1215',
      issueDate : new Date(),
      expirationDate : new Date()
    };
    this.message = 'Success buy ticket ' + this.ticket.serialNo;
  
  }


}
