import { Injectable } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { LineDTO } from 'src/app/model/LineDTO';
import { BehaviorSubject } from 'rxjs';
import { StationServiceService } from '../stations/station-service.service';
import { LineService } from '../lines/line.service';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  
  private stationSource = new BehaviorSubject<StationDTO[]>([]);
  private lineSource = new BehaviorSubject<LineDTO[]>([]);

  stations = this.stationSource.asObservable();
  lines = this.lineSource.asObservable();

  constructor(private stationService: StationServiceService, private lineService: LineService) { }

  updateStations() {
    this.stationService.getAll().subscribe(data => {
      this.stationSource.next(data);     
    });
  }

  updateLines() {
    this.lineService.getAll().subscribe(data => {
      this.lineSource.next(data);     
    });
  }

  updateAll() {
    this.updateStations();
    this.updateLines();
  }

}
