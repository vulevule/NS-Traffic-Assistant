import { Component, OnInit } from "@angular/core";
import { LineDTO } from "src/app/model/LineDTO";
import { StationDTO } from "src/app/model/StationDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";
import { UserDTO } from "src/app/model/UserDTO";

@Component({
  selector: "app-stations",
  templateUrl: "./stations.component.html",
  styleUrls: ["./stations.component.css", "../general.scss"]
})
export class StationsComponent implements OnInit {
  stations: StationDTO[];
  lines: LineDTO[];

  activeTab: String;

  loggedUser: UserDTO;

  constructor(private sharedService: SharedService) {}

  ngOnInit() {
    this.loggedUser = JSON.parse(localStorage.getItem("currentUser"));

    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );
    this.sharedService.lines.subscribe(lines => (this.lines = lines));

    if (this.stations.length === 0 || this.lines.length === 0) {
      this.sharedService.updateAll();
    }
  }

  onTabChange($event: any) {
    this.activeTab = $event.nextId;
  }
}

