import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { PriceListInterface } from 'src/app/model/Pricelist';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PriceListServiceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  readonly pricelistUrl = "/api/pricelist";

  constructor(private http: HttpClient) { }


  //doabvanje cenovnika 
  getPricelist(): Observable<PriceListInterface> {
    return this.http.get<PriceListInterface>(`${this.pricelistUrl}/getPricelist`);
  }

  //kreiranje novog rasporeda
  addPricelist(p: PriceListInterface): Observable<String> {
    return this.http.post(`${this.pricelistUrl}/addPricelist`, p, { headers: this.headers, responseType: "text" });
  }


}
