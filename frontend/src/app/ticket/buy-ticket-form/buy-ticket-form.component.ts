import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-buy-ticket-form',
  templateUrl: './buy-ticket-form.component.html',
  styleUrls: ['./buy-ticket-form.component.css', './general.scss']
})
export class BuyTicketFormComponent implements OnInit {

  @Input() role: String;
  public buyTicket: TicketInterface;
  ticket: TicketInterface;
  message: String = '';
  infoType: String;

  type: any = 'BUS';
  time: any = 'ANNUAL';
  zone: any = 'FIRST';



  constructor(private ticketService: TicketServiceService) { }

  ngOnInit() {
    this.buyTicket = {
      price: 0,
      trafficType: this.type,
      trafficZone: this.zone,
      timeType: this.time,
    }

    this.getPrice();
  }


  getPrice() {
    //ovde cemo pozivati funkciju sa back-a za dobavljanje cene 
    this.buyTicket = {
      price: 0,
      trafficType: this.type,
      trafficZone: this.zone,
      timeType: this.time,
    }

    this.ticketService.getPrice(this.buyTicket)
      .subscribe(data => {
        this.buyTicket.price = +data;
      }, error => {
        this.message = error.error;
        this.infoType = 'danger';
      })
  }


  buy() {
    //pozvatu metodu iz servisa za kupovinu karata
    //ovde samo pozovemo metodu za kupovinu karte
    this.buyTicket = {
      trafficType: this.type,
      trafficZone: this.zone,
      timeType: this.time,
    }
    this.ticketService.buyTicket(this.buyTicket)
      .subscribe(data => {
        this.message = "Ticket is bought!!";
        this.infoType = 'success';
        this.ticket = data;
      },
        error => {
          this.message = error.error;
          this.infoType = 'danger';
        }
      );

  }


}
