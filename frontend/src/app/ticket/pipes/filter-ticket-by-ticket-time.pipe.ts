import { Pipe, PipeTransform } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';

@Pipe({
  name: 'filterTicketByTicketTime',
  pure: false
})
export class FilterTicketByTicketTimePipe implements PipeTransform {

  transform(tickets: TicketInterface[], type?: { annual: boolean, month: boolean, single: boolean, daily: boolean }): TicketInterface[] {
    if (tickets) {
      if (!type.annual && !type.month && !type.single && !type.daily) {
        return tickets;
      }
      return tickets.filter((ticket: TicketInterface) => this.applyFilter(ticket, type));
    }
  }

  applyFilter(ticket: TicketInterface, type: { annual: boolean, month: boolean, single: boolean, daily: boolean }): boolean {
    if (type.annual && ticket.timeType === 'ANNUAL') {
      return true;
    }

    if (type.month && ticket.timeType === 'MONTH') {
      return true;
    }

    if (type.daily && ticket.timeType === 'DAILY') {
      return true;
    }


    if (type.single && ticket.timeType === 'SINGLE') {
      return true;
    }
  }
}
