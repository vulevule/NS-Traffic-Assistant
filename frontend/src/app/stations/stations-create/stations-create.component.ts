import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { StationServiceService } from 'src/app/services/stations/station-service.service';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/services/sharedVars/shared.service';

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

  handleStationClick(station: StationDTO) {

  }

}
