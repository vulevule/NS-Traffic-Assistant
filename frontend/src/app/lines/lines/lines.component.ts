import { Component, OnInit } from "@angular/core";
import { LineDTO } from "src/app/model/LineDTO";
import { StationDTO } from "src/app/model/StationDTO";
import {UserDTO} from "src/app/model/UserDTO";
import { SharedService } from "src/app/services/sharedVars/shared.service";

@Component({
  selector: "app-lines",
  templateUrl: "./lines.component.html",
  styleUrls: ["./lines.component.css", "./general.scss"]
})
export class LinesComponent implements OnInit {
  lines: LineDTO[];
  stations: StationDTO[];
  loggedUser : UserDTO;
  role : String = '';

  activeTab: String;
  selectedLine: LineDTO;

  constructor(private sharedService: SharedService) {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    if(this.loggedUser !== null){
      this.role = this.loggedUser.role;
    }
  }

  ngOnInit() {
    this.sharedService.stations.subscribe(
      stations => (this.stations = stations)
    );
    this.sharedService.lines.subscribe(lines => (this.lines = lines));
    
    //this.selectedLine = new LineDTO();
  }

  openEditor(line: LineDTO) {
    console.log(line);
    this.selectedLine = JSON.parse(JSON.stringify(line));
    this.activeTab = "editLineTab";
    
  }

  onTabChange($event) {
    if($event.nextId !== "editLineTab") {
      this.selectedLine = undefined;
    }
    this.activeTab = $event.nextId;
    
  }
}
