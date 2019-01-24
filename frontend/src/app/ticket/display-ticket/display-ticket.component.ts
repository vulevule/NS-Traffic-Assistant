import { Component, OnInit , Input} from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UseCheckTicketComponent } from '../use-check-ticket/use-check-ticket.component';

@Component({
  selector: 'app-display-ticket',
  templateUrl: './display-ticket.component.html',
  styleUrls: ['./display-ticket.component.css', './general.scss']
})
export class DisplayTicketComponent implements OnInit {

  @Input() ticket : TicketInterface;

  @Input()  role : string;



  constructor(private modalService : NgbModal) { }

  ngOnInit() {
  }


  openDialog(){
    const modalRef = this.modalService.open(UseCheckTicketComponent);
    modalRef.componentInstance.role = this.role;
    modalRef.componentInstance.ticket = this.ticket;
  }


}
