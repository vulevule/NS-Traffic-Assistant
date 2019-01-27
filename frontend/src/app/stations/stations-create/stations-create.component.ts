import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { SharedService } from 'src/app/services/sharedVars/shared.service';
import { StationServiceService } from 'src/app/services/stations/station-service.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-stations-create',
  templateUrl: './stations-create.component.html',
  styleUrls: ['./stations-create.component.css', '../general.scss']
})
export class StationsCreateComponent implements OnInit {

  @Input()
  stations: StationDTO[];

  @Output()
  onCreation = new EventEmitter<String>();

  newStation: StationDTO;
  markerOnMap: any;

  displayType = {
    bus: true,
    tram: false,
    metro: false
  };

  private displayTypeSubject: Subject<any> = new Subject<any>();

  constructor(private stationService: StationServiceService, private sharedService: SharedService) { }

  ngOnInit() {
    this.newStation = new StationDTO();
  }

  createStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];
    station.lines = [];

    this.stationService.createStation(station).subscribe(
      data => {
        //this.toaster.success(data);
        alert(data);
        this.sharedService.updateAll();

        this.onCreation.emit("displayStationsTab");
      },
      reason => {
        //this.toaster.error(reason.error);
        alert(reason.error);
      }
    );
  }

  emitDisplayTypeToMap() {
    this.displayTypeSubject.next(this.displayType);
  }

  handleStationClick(station: StationDTO) {

  }

}
