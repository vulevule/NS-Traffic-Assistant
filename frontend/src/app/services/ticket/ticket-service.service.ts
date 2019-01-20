import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Ticket, BuyTicket } from 'src/app/model/Ticket';
import { Report } from 'src/app/model/Report';

@Injectable({
  providedIn: 'root'
})
export class TicketServiceService {

  private headers = new HttpHeaders({"Content-Type" : "application/json"});
  private ticketUrl = "/api/ticket";
  size : number;

  constructor(private http : HttpClient) { }

  //proba
  getAll(page:number):Promise<Ticket[]>{
    this.size = 4;
    return this.http
      .get<Ticket[]>(`${this.ticketUrl}/all?page=${page}&size=${this.size}`)
      .toPromise()
      .then(response => response as Ticket[])
      .catch(this.handleError);
  } 

  //sve karte od jednog korisnika, koji je trenutno ulogovan 
  getMyTicket(page:number):Promise<Ticket[]>{
    this.size = 4;
    return this.http
      .get<Ticket[]>(`${this.ticketUrl}/myTicket?page=${page}&size=${this.size}`)
      .toPromise()
      .then(response => response as Ticket[])
      .catch(this.handleError);
  }

  //kupovina karte
  buyTicket(t : BuyTicket) : Promise<Ticket>{
    return this.http
      .post<Ticket>(`${this.ticketUrl}/buyTicket`, JSON.stringify(t),{
        headers : this.headers
      })
      .toPromise()
      .then(response => response as Ticket)
      .catch(this.handleError);
  }

/*
  //koriscenje karte
  useTicket() : Promise<String>{
    ret
  }


  //cekiranje karte od strane inspektora*/


  //dobavljanje cene karte 

  getPrice(t : BuyTicket):Promise<number>{
    return this.http
      .get<number>(`${this.ticketUrl}/price?type=${t.trafficType}&zone=${t.trafficZone}&time=${t.timeType}`)
      .toPromise()
      .then(response => response as number)
      .catch(this.handleError);
  }

/*
treba nam zbog stranicenja podataka 
*/
  getNumOfTicket():Promise<number>{
    return this.http
      .get<number>(`${this.ticketUrl}/size`)
      .toPromise()
      .then(response => response as number)
      .catch(this.handleError);
  }


  getReport(month : string, year : number, type : string): Promise<Report>{
    var m : number = this.convertMonth(month);
    return this.http
      .get<Report>(`${this.ticketUrl}/report?month=${m}&year=${year}&type=${type}`)
      .toPromise()
      .then(response => response as Report)
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any>{
    console.error("Error...", error);
    return Promise.reject(error.message || error);
  }

  convertMonth(month : string): number{
    var m = month.toUpperCase();
    if(m === 'JANUARY'){
      return 1;
    }else if (m === 'FEBRUARY'){
      return 2;
    }else if (m === 'MARCH'){
      return 3;
    }else if(m === 'APRIL'){
      return 4;
    }else if(m === 'MAY'){
      return 5;
    }else if (m === 'JUNE'){
      return 6;
    }else if (m === 'JULY'){
      return 7;
    }else if(m === 'AUGUST'){
      return 8;
    }else if (m === 'SEPTEMBER'){
      return 9;
    }else if(m === 'OCTOBER'){
      return 10;
    }else if(m === 'NOVEMBER'){
      return 11;
    }else if(m === 'DECEMBER'){
      return 12;
    }else{
      return 0;
    }
  }



}
