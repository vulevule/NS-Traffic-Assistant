import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/model/Ticket';



@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css', './general.scss']
})
export class TicketComponent implements OnInit {

  ticket : Ticket; 

  constructor() { }

  ngOnInit() {
  }

  displayTicket($event){
    //prvi parametar je poslata vrednost iz potomka 
    this.ticket = $event;
  }

}
