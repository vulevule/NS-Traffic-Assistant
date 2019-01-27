import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient} from '@angular/common/http';
import { TimetableItemInterface,  TimetableDtoInterface } from 'src/app/model/Timetable';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TimetableService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  timetableUrl : String = "/api/timetable";

  constructor(private http : HttpClient) { }


  //povucemo redove voznje za sve linije po zoni i tipu prevoza
  getAllTimetableByZoneAndTrafficType (zone : String, type :  String): Observable<TimetableItemInterface[]>{
    return this.http.get<TimetableItemInterface[]>(`${this.timetableUrl}/getItemByTrafficTypeAndZone?zone=${zone}&type=${type}`);
  }

  //kreiranje novog reda voznje 
  createTimetable( timetable : TimetableDtoInterface) : Observable<String> {
    return this.http.post(`${this.timetableUrl}/addTimetable`,  JSON.stringify(timetable), {headers : this.headers, responseType : "text"});
  }
}
