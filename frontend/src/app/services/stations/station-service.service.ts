import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StationDTO } from 'src/app/model/StationDTO';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class StationServiceService {

  constructor(private http:HttpClient) { }

  getAll():Observable<StationDTO[]> {
    return this.http.get<StationDTO[]>('/api/station/getAll');
  }

  getAllByType(type:String):Observable<StationDTO[]> {
    return this.http.get<StationDTO[]>('/api/station/getAllByType/' + type);
  }

  getAllByLine(id:any):Observable<StationDTO[]> {
    return this.http.get<StationDTO[]>('/api/station/getAllByLine/' + id);
  }

  getByNameAndType(name:String, type:String):Observable<StationDTO> {
    return this.http.get<StationDTO>('/api/station/getByNameAndType/' + name + '/' + type);
  }

  getById(id:any):Observable<StationDTO> {
    return this.http.get<StationDTO>('/api/station/getById/' + id);
  }

  createStation(station:StationDTO):Observable<StationDTO> {
    return this.http.post<StationDTO>('/api/station/create', station);
  }

  updateStation(station:StationDTO):Observable<StationDTO> {
    return this.http.put<StationDTO>('/api/station/update', station);
  }

  deleteStation(id:any) {
    return this.http.delete('/api/station/delete/' + id);
  }
}
