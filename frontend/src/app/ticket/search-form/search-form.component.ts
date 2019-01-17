import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/model/Ticket';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css', './general.scss']
})
export class SearchFormComponent implements OnInit {

  tickets : Ticket[];
  

  constructor() { }

  ngOnInit() {
    this.tickets = [{
      id: 12,
      trafficType: 'BUS',
      trafficZone: 'FIRST',
      ticketTime: 'ANNUAL',
      price: 12000,
      userTicketType: 'STUDENT',
      serialNo: 'JBHGHAGHD1215',
      issueDate : new Date(),
      expirationDate : new Date()
    },{
      id: 12,
      trafficType: 'METRO',
      trafficZone: 'FIRST',
      ticketTime: 'ANNUAL',
      price: 12000,
      userTicketType: 'STUDENT',
      serialNo: 'JBHGHAGHD1215',
      issueDate : new Date(),
      expirationDate : new Date()
    },{
      id: 12,
      trafficType: 'TRAM',
      trafficZone: 'FIRST',
      ticketTime: 'ANNUAL',
      price: 12000,
      userTicketType: 'STUDENT',
      serialNo: 'JBHGHAGHD1215',
      issueDate : new Date(),
      expirationDate : new Date()
    }];
  }
    

}
