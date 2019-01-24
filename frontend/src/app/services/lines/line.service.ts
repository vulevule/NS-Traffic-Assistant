import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LineDTO } from 'src/app/model/LineDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
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

  createLine(line: LineDTO): Promise<LineDTO> {
    return this.http
      .post<LineDTO>(`${this.linesUrl}/create`, JSON.stringify(line), {
        headers: this.headers
      })
      .toPromise()
      .then(response => response as LineDTO)
      .catch(this.handleError);
  }

  updateLine(line: LineDTO): Promise<LineDTO> {
    return this.http
      .put<LineDTO>(`${this.linesUrl}/update`, JSON.stringify(line), {
        headers: this.headers
      })
      .toPromise()
      .then(response => response as LineDTO)
      .catch(this.handleError);
  }

  deleteLine(id: any): Promise<{}> {
    return this.http
      .delete(`${this.linesUrl}/delete/${id}`)
      .toPromise()
      .catch(this.handleError);
  }


  getAllByZoneAndTrafficType(zone : String, type : String):Observable<LineDTO[]>{
    return this.http.get<LineDTO[]>(`${this.linesUrl}/getAllByZoneAndTrafficType?zone=${zone}&type=${type}`);
  }

  handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
