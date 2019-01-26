import { Pipe, PipeTransform } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';

@Pipe({
  name: 'filterTicketBySno', 
  pure : true
})
export class FilterTicketBySnoPipe implements PipeTransform {

  transform(tickets: TicketInterface[], sno?: any): TicketInterface[] {
    if(tickets){
      if(sno === '') {
        return tickets;
      }
      return tickets.filter((ticket:TicketInterface)=> this.applyFilter(ticket, sno));
    }
  }

  applyFilter(ticket: TicketInterface, sno : any):boolean{
    if(ticket.serialNo === sno){
      return true;
    }

    

  }

}
