import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-buy-ticket-form',
  templateUrl: './buy-ticket-form.component.html',
  styleUrls: ['./buy-ticket-form.component.css', './general.scss']
})
export class BuyTicketFormComponent implements OnInit {

  public buyTicket: TicketInterface;
  ticket: TicketInterface;
  message : String = '';
  infoType : String;





  constructor(private ticketService : TicketServiceService, private auth : AuthenticationService, private toastr: ToastrService) { }

  async ngOnInit() {
    this.buyTicket = {
      price: 0,
      trafficType: 'BUS',
      trafficZone: 'FIRST',
      timeType: 'ANNUAL',
    }
    this.getPrice();
  }


  async getPrice() {
    //ovde cemo pozivati funkciju sa back-a za dobavljanje cene 
    this.ticketService.getPrice(this.buyTicket)
    .subscribe(data => {
      this.buyTicket.price = data;
    }, error =>{
      alert(error.message);
    })
  }


  async buy() {
    //pozvatu metodu iz servisa za kupovinu karata
    //ovde samo pozovemo metodu za kupovinu karte
    this.ticketService.buyTicket(this.buyTicket)
    .subscribe(data => {
      this.message = "Ticket is buy!!";
      this.ticket = data;
      this.infoType = 'success';
    },
      error => {
        this.message = error.error;
        this.infoType = 'danger';
      } 
    );
    
  }




}
