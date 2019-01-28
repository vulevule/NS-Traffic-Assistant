import { Component, OnInit, Input } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { LineDTO } from 'src/app/model/LineDTO';
import { Observable } from 'rxjs';
import { debounceTime, map } from 'rxjs/operators';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LineService } from 'src/app/services/lines/line.service';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';


@Component({
  selector: 'app-use-check-ticket',
  templateUrl: './use-check-ticket.component.html',
  styleUrls: ['./use-check-ticket.component.css', './general.scss']
})
export class UseCheckTicketComponent implements OnInit {
  @Input() ticket: TicketInterface;
  @Input() role: string;

  lines: LineDTO[];
  selectLine: any;
  message: string = '';
  alertType: string;

  search = (text$: Observable<String>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term === '' ? []
        : this.lines.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0.10)
      )
    )

  formatter = (x: { name: String }) => x.name;


  constructor(public activeModal: NgbActiveModal, public lineService: LineService, public ticketService: TicketServiceService) { }



  ngOnInit() {
    this.lineService.getAll().
      subscribe(data => {
        this.lines = data;
      })
  }

  ok() {
    if (this.selectLine === undefined) {
      this.message = 'Please choose a line.';
      this.alertType = 'info';
    } else {
      if (this.role === 'INSPECTOR') {
        //pozivamo check metodu
        this.ticketService.checkTicket(this.ticket.serialNo, this.selectLine.id)
          .subscribe(data => {
            this.message = data;
            this.alertType = 'success';
          },
            error => {
              this.message = error.error;
              this.alertType = 'danger';
            }
          )
      } else {
        //putnik pozivamo use metodu
        this.ticketService.useTicket(this.ticket.serialNo, this.selectLine.id)
          .subscribe(data => {
            this.message = data;
            this.alertType = 'success';
          },
            error => {
              this.message = error.error;
              this.alertType = 'danger';
            })
      }
    }
  }

}
