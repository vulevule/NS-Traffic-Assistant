import { Pipe, PipeTransform } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';

@Pipe({
  name: 'filterTicketByZone',
  pure : false
})
export class FilterTicketByZonePipe implements PipeTransform {

  transform(tickets: TicketInterface[], type?: {first:boolean, second:boolean}): TicketInterface[] {
    if(tickets){
      if(!type.first && !type.second) {
        return tickets;
      }
      return tickets.filter((ticket:TicketInterface)=> this.applyFilter(ticket, type));
    }
  }

  applyFilter(ticket: TicketInterface, type : {first:boolean, second:boolean}):boolean{
    if(type.first && ticket.trafficZone==='FIRST'){
      return true;
    }

    if(type.second && ticket.trafficZone==='SECOND'){
      return true;
    }

  }

}
