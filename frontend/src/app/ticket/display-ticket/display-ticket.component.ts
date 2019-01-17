import { Component, OnInit , Input} from '@angular/core';
import { Ticket } from 'src/app/model/Ticket';

@Component({
  selector: 'app-display-ticket',
  templateUrl: './display-ticket.component.html',
  styleUrls: ['./display-ticket.component.css', './general.scss']
})
export class DisplayTicketComponent implements OnInit {

  @Input() ticket : Ticket;


  constructor() { }

  ngOnInit() {
  }

}
