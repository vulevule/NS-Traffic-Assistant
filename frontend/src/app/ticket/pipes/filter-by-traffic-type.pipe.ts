import { Pipe, PipeTransform } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';

@Pipe({
  name: 'filterByTrafficType',
  pure: false
})
export class FilterByTrafficTypePipe implements PipeTransform {
  //ovde cemo filtrirati karte po tipu prevoza, bus, metro ili tram 
  transform(tickets: TicketInterface[], type?: { bus: boolean, tram: boolean, metro: boolean }): TicketInterface[] {
    if (tickets) {
      if (!type.bus && !type.metro && !type.tram) {
        return tickets;
      }
      return tickets.filter((ticket: TicketInterface) => this.applyFilter(ticket, type));
    }
  }

  applyFilter(ticket: TicketInterface, type: { bus: boolean, tram: boolean, metro: boolean }): boolean {
    if (type.bus && ticket.trafficType === 'BUS') {
      return true;
    }

    if (type.tram && ticket.trafficType === 'TRAM') {
      return true;
    }

    if (type.metro && ticket.trafficType === 'METRO') {
      return true;
    }
  }
}
