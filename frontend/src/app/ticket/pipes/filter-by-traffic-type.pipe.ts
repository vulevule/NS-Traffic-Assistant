import { Pipe, PipeTransform } from '@angular/core';
import { Ticket } from 'src/app/model/Ticket';
import { tick } from '@angular/core/testing';

@Pipe({
  name: 'filterByTrafficType'
})
export class FilterByTrafficTypePipe implements PipeTransform {
//ovde cemo filtrirati karte po tipu prevoza, bus, metro ili tram 
  transform(tickets: Ticket[], type?: {bus:boolean, tram:boolean, metro:boolean}): Ticket[] {
    if(tickets){
      return tickets.filter((ticket:Ticket)=> this.applyFilter(ticket, type));
    }
  }

  applyFilter(ticket: Ticket, type : {bus:boolean, tram:boolean, metro:boolean}):boolean{
    if(type.bus && ticket.trafficType==='BUS'){
      return true;
    }

    if(type.tram && ticket.trafficType==='TRAM'){
      return true;
    }

    if(type.metro && ticket.trafficType==='METRO'){
      return true;
    }
  }
}
