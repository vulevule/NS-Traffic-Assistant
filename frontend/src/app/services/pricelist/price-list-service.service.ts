import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { PriceListReaderDto, PriceList } from 'src/app/model/Pricelist';


@Injectable({
  providedIn: 'root'
})
export class PriceListServiceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private pricelistUrl = "/api/pricelist";

  constructor(private http: HttpClient) { }


  //doabvanje cenovnika 
  getPricelist():Promise<PriceListReaderDto>{
    return this.http
      .get<PriceListReaderDto>(`${this.pricelistUrl}/getPricelist`)
      .toPromise()
      .then(response => response as PriceListReaderDto)
      .catch(this.handleError);
  }

  //kreiranje novog rasporeda
  addPricelist(p : PriceList) : Promise<PriceListReaderDto>{
    return this.http
      .post<PriceListReaderDto>(`${this.pricelistUrl}/addPricelist`, JSON.stringify(p),{
        headers : this.headers})
        .toPromise()
        .then(response => response as PriceListReaderDto)
        .catch(this.handleError);
  }

  handleError(error: any): Promise<any>{
    console.error("Error...", error);
    return Promise.reject(error.message || error);
  }


}
