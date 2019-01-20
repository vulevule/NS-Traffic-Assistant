import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BuyTicket, Ticket } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';

@Component({
  selector: 'app-buy-ticket-form',
  templateUrl: './buy-ticket-form.component.html',
  styleUrls: ['./buy-ticket-form.component.css', './general.scss']
})
export class BuyTicketFormComponent implements OnInit {

  public buyTicket: BuyTicket;
  ticket: Ticket;
  message : String;



  constructor(private ticketService : TicketServiceService) { }

  async ngOnInit() {
    this.buyTicket = {
      price: 0,
      trafficType: 'BUS',
      trafficZone: 'FIRST',
      timeType: 'ANNUAL',
    }
    this.message = '';
  }


  async getPrice() {
    //ovde cemo pozivati funkciju sa back-a za dobavljanje cene 
    await this.ticketService.getPrice(this.buyTicket)
    .then(data => {
      alert(`Price is ${data}`);
      this.buyTicket.price = data;
    }, reason =>{
      alert('Error');
    })
  }


  async buy() {
    //pozvatu metodu iz servisa za kupovinu karata
    //ovde samo pozovemo metodu za kupovinu karte
    await this.ticketService.buyTicket(this.buyTicket)
    .then(data => {
      this.message = "Ticket is buy!!";
      this.ticket = data;
    },
      reason => {
        this.message = "ERROR!!!";
      } 
    );
    
  }


}
