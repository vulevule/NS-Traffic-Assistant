import { Component, OnInit } from "@angular/core";
import { LineDTO } from "src/app/model/LineDTO";
import { StationDTO } from "src/app/model/StationDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";

@Component({
  selector: "app-lines",
  templateUrl: "./lines.component.html",
  styleUrls: ["./lines.component.css", "./general.scss"]
})
export class LinesComponent implements OnInit {
  lines: LineDTO[];
  stations: StationDTO[];

  activeTabId: String;

  constructor(private sharedService: SharedService) {}

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );
    this.sharedService.lines.subscribe(lines => (this.lines = lines));
    
  }

  tabChange(tabId: String) {
    this.activeTabId = tabId;
  }
}
