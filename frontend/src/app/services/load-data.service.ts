import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class LoadDataService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private linesUrl = "/api";
  constructor(private http: HttpClient) {}

  loadData(name: String): Observable<String> {
    return this.http.get(`${this.linesUrl}/loadData/${name}`, {
      responseType: "text"
    });
  }
}
