import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { TicketInterface, TicketReaderInterface } from 'src/app/model/Ticket';
import { ReportInterface } from 'src/app/model/Report';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketServiceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private ticketUrl = "/api/ticket";
  size: number;

  constructor(private http: HttpClient) { }


  /*
  treba nam zbog stranicenja podataka 
  */
  getNumOfTicket(): Observable<number> {
    return this.http
      .get<number>(`${this.ticketUrl}/size`);
  }
  getMyTicket(page: number): Observable<TicketReaderInterface> {
    this.size = 4;
    return this.http
      .get<TicketReaderInterface>(`${this.ticketUrl}/myTicket?page=${page - 1}&size=${this.size}`);
  }


  //da bi prikazali inspektoru da bi mogao da izabere kartu za cekiranje
  getAllTickets(page: number): Observable<TicketReaderInterface> {
    this.size = 4;
    return this.http.get<TicketReaderInterface>(`${this.ticketUrl}/all?page=${page - 1}&size=${this.size}`);
  }




  //kupovina karte
  buyTicket(t: TicketInterface): Observable<TicketInterface> {
    return this.http
      .post<TicketInterface>(`${this.ticketUrl}/buyTicket`, JSON.stringify(t), {
        headers: this.headers
      });
  }



  //cekiranje karte od strane inspektora*/
  checkTicket(sno: String, line_id: number): Observable<string> {
    return this.http.get(`${this.ticketUrl}/checkTicket?serialNo=${sno}&line=${line_id}`, { responseType: 'text' });
  }

  //upotreba karte od strane putnika
  useTicket(sno: string, line_id: Number): Observable<string> {
    return this.http.get(`${this.ticketUrl}/useTicket?serialNo=${sno}&line=${line_id}`,{responseType: 'text' });
  }


  //dobavljanje cene karte 

  getPrice(t: TicketInterface): Observable<number> {
    return this.http
      .get<number>(`${this.ticketUrl}/price?type=${t.trafficType}&zone=${t.trafficZone}&time=${t.timeType}`);
  }



  getReport(month: number, year: number, type: string): Observable<ReportInterface> {
    return this.http
      .get<ReportInterface>(`${this.ticketUrl}/report?month=${month}&year=${year}&type=${type}`)
     
  }



 



}
