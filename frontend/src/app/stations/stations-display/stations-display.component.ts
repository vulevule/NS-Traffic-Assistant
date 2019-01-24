import { Component, OnInit, Input } from "@angular/core";
import { debounceTime, distinctUntilChanged, map } from "rxjs/operators";
import { Observable, Subject } from "rxjs";
import { StationDTO } from "src/app/model/StationDTO";
import { LineDTO } from "src/app/model/LineDTO";
import { StationServiceService } from "src/app/services/stations/station-service.service";
import { SharedService } from "src/app/services/sharedVars/shared.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-stations-display",
  templateUrl: "./stations-display.component.html",
  styleUrls: ["./stations-display.component.css", '../general.scss']
})
export class StationsDisplayComponent implements OnInit {
  @Input()
  stations: StationDTO[];
  @Input()
  lines: LineDTO[];

  editStation: StationDTO;

  selectedStation: StationDTO;
  selectedLine: LineDTO;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.stations
              .filter(
                s =>
                  s.name.toLowerCase().indexOf(term.toLowerCase()) > -1 &&
                  this.displayType[s.type.toLowerCase()]
              )
              .slice(0, 20)
      )
    );

  formatter = (result: StationDTO) => result.name;

  searchByLine = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term =>
        term.length < 2
          ? []
          : this.lines
              .filter(
                s =>
                  s.name.toLowerCase().indexOf(term.toLowerCase()) > -1 &&
                  this.displayType[s.type.toLowerCase()]
              )
              .slice(0, 20)
      )
    );

  formatterLine = (result: LineDTO) => result.name;

  displayType = {
    bus: true,
    tram: false,
    metro: false
  };

  private displayTypeSubject: Subject<any> = new Subject<any>();
  private selectedStationSubject: Subject<StationDTO> = new Subject<StationDTO>();

  markerOnMap: any;

  constructor(
    private stationService: StationServiceService,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => this.stations = stations
    );
    this.sharedService.lines.subscribe(lines => this.lines = lines);
  }

  selectStation(station: StationDTO) {
    this.selectedStation = JSON.parse(JSON.stringify(station));
    this.editStation = undefined;
    this.emitSelectedStationToMap();
  }

  updateStation(station: StationDTO) {
    station.xCoordinate = this.markerOnMap[0];
    station.yCoordinate = this.markerOnMap[1];

    this.stationService.updateStation(station).subscribe(
      data => {
        //this.toaster.success(data);
        alert(data);

        this.selectedStation = station;
        this.editStation = undefined;

        this.sharedService.updateAll();

        this.emitDisplayTypeToMap();
      },
      reason => {
        //this.toaster.error(reason.error);
        alert(reason.error);
      }
    );
  }

  deleteStation(station: StationDTO) {
    if (
      confirm(`You are about to delete ${station.name} station.\nAre you sure?`)
    ) {
      this.stationService.deleteStation(station.id).subscribe(
        data => {
          //this.toaster.success(data);
          alert(data);

          this.selectedStation = undefined;

          this.sharedService.updateAll();

          this.emitDisplayTypeToMap();
        },
        reason => {
          //this.toaster.error(reason.error);
         alert(reason.error);
        }
      );
    }
  }

  emitSelectedStationToMap() {
    this.selectedStationSubject.next(this.selectedStation);
  }

  emitDisplayTypeToMap() {
    this.displayTypeSubject.next(this.displayType);
  }
}
