import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TimetableItemInterface } from 'src/app/model/Timetable';

@Injectable({
  providedIn: 'root'
})
export class TimetableService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  timetableUrl : String = "/api/timetable";

  constructor(private http : HttpClient) { }


  getTimetableItemsByLineAndType(line_id : number, type : String): Observable<TimetableItemInterface>{
    return this.http.get<TimetableItemInterface>(`${this.timetableUrl}/getItemByLineAndType?line=${line_id}&type=${type}`);
  }
}
