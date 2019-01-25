import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LineDTO } from 'src/app/model/LineDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: "root"
})
export class LineService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private linesUrl = "/api/line";

  constructor(private http: HttpClient) {}

  getAll(): Observable<LineDTO[]> {
    return this.http
      .get<LineDTO[]>(`${this.linesUrl}/getAll`);
  }

  getAllByType(type: String): Promise<LineDTO[]> {
    return this.http
      .get<LineDTO[]>(`${this.linesUrl}/getAllByType/${type}`)
      .toPromise()
      .then(response => response as LineDTO[])
      .catch(this.handleError);
  }

  getAllByStation(id: any): Promise<LineDTO[]> {
    return this.http
      .get<LineDTO[]>(`${this.linesUrl}/getAllByStation/${id}`)
      .toPromise()
      .then(response => response as LineDTO[])
      .catch(this.handleError);
  }

  getByNameAndType(name: String, type: String): Promise<LineDTO> {
    return this.http
      .get<LineDTO>(`${this.linesUrl}/getByNameAndType/${name}/${type}`)
      .toPromise()
      .then(response => response as LineDTO)
      .catch(this.handleError);
  }

  getById(id: any): Promise<LineDTO> {
    return this.http
      .get<LineDTO>(`${this.linesUrl}/getById/${id}`)
      .toPromise()
      .then(response => response as LineDTO)
      .catch(this.handleError);
  }

  createLine(line: LineDTO): Observable<String> {
    return this.http.post(`${this.linesUrl}/create`, line, {
      headers: this.headers,
      responseType: "text"
    });
  }

  updateLine(line: LineDTO): Observable<String> {
    return this.http.put(`${this.linesUrl}/update`, line, {
      headers: this.headers,
      responseType: "text"
    });
  }

  deleteLine(id: any): Observable<String> {
    return this.http.delete(`${this.linesUrl}/delete/${id}`, {
      responseType: "text"
    });
  }


  getAllByZoneAndTrafficType(zone : String, type : String):Observable<LineDTO[]>{
    return this.http.get<LineDTO[]>(`${this.linesUrl}/getAllByZoneAndTrafficType?zone=${zone}&type=${type}`);
  }

  handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.error);
  }
}
