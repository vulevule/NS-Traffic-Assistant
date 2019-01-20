import { Component, OnInit, Input } from '@angular/core';


@Component({
  selector: 'app-use-check-ticket',
  templateUrl: './use-check-ticket.component.html',
  styleUrls: ['./use-check-ticket.component.css', './general.scss']
})
export class UseCheckTicketComponent implements OnInit {
  serialNo : string = '';

 

  constructor() { }


  ngOnInit() {
  }

}
