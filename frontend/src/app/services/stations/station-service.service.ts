import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { StationDTO } from "src/app/model/StationDTO";

@Injectable({
  providedIn: "root"
})
export class StationServiceService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private stationsUrl = "/api/station";

  constructor(private http: HttpClient) {}

  getAll(): Promise<StationDTO[]> {
    return this.http
      .get<StationDTO[]>(`${this.stationsUrl}/getAll`)
      .toPromise()
      .then(response => response as StationDTO[])
      .catch(this.handleError);
  }

  getAllByType(type: String): Promise<StationDTO[]> {
    return this.http
      .get<StationDTO[]>(`${this.stationsUrl}/getAllByType/${type}`)
      .toPromise()
      .then(response => response as StationDTO[])
      .catch(this.handleError);
  }

  getAllByLine(id: any): Promise<StationDTO[]> {
    return this.http
      .get<StationDTO[]>(`${this.stationsUrl}/getAllByLine/${id}`)
      .toPromise()
      .then(response => response as StationDTO[])
      .catch(this.handleError);
  }

  getByNameAndType(name: String, type: String): Promise<StationDTO> {
    return this.http
      .get<StationDTO>(`${this.stationsUrl}/getByNameAndType/${name}/${type}`)
      .toPromise()
      .then(response => response as StationDTO)
      .catch(this.handleError);
  }

  getById(id: any): Promise<StationDTO> {
    return this.http
      .get<StationDTO>(`${this.stationsUrl}/getById/${id}`)
      .toPromise()
      .then(response => response as StationDTO)
      .catch(this.handleError);
  }

  createStation(station: StationDTO): Promise<StationDTO> {
    return this.http
      .post<StationDTO>(`${this.stationsUrl}/create`, JSON.stringify(station), {
        headers: this.headers
      })
      .toPromise()
      .then(response => response as StationDTO)
      .catch(this.handleError);
  }

  updateStation(station: StationDTO): Promise<StationDTO> {
    return this.http
      .put<StationDTO>(`${this.stationsUrl}/update`, JSON.stringify(station), {
        headers: this.headers
      })
      .toPromise()
      .then(response => response as StationDTO)
      .catch(this.handleError);
  }

  deleteStation(id: any): Promise<{}> {
    return this.http
      .delete(`${this.stationsUrl}/delete/${id}`)
      .toPromise()
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
