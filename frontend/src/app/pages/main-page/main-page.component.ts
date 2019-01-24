import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/model/User';
import { LoggedUserService } from 'src/app/services/loggedUserService';
import { Router } from '@angular/router';
import { StationDTO } from 'src/app/model/StationDTO';
import { LineDTO } from 'src/app/model/LineDTO';
import { StationServiceService } from 'src/app/services/stations/station-service.service';
import { LineService } from 'src/app/services/lines/line.service';
import { SharedService } from 'src/app/services/sharedVars/shared.service';
import { UserDTO } from 'src/app/model/UserDTO';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css', './general.scss']
})
export class MainPageComponent implements OnInit {

  loggedUser: UserDTO;

  stations: StationDTO[];
  lines: LineDTO[];

  constructor(private loggedUserService: LoggedUserService, private router: Router, private sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.stations.subscribe(stations => this.stations = stations);
    this.sharedService.lines.subscribe(lines => this.lines = lines);

    this.sharedService.updateAll();

    
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));

    if(this.loggedUser === null){
      alert('user je null');
    }
    // Ubaciti proveru ako nije ulogovan (loggedUser je undefined) redirektovati na login stranicu
    // na logoutu postaviti loggedUser na undefined

    //  if(!this.loggedUser) {
    //   //alert("No logged user\nI will redirect you to login page in 3 seconds");
    //   var router = this.router;
    //   //setTimeout(function() {
    //     var element=document.getElementById('tickets') as HTMLElement;
    //     element.hidden=true;
    //     var element1=document.getElementById('editItem') as HTMLElement;
    //     element.hidden=true;
    //     var element2=document.getElementById('loginItem') as HTMLElement;
    //     element.hidden=false;
    //     var element2=document.getElementById('registerItem') as HTMLElement;
    //     element.hidden=false;
    //     router.navigate(["/main"]);
    //   //}, 3000, router);
    // } 
    // else{
    //   var element=document.getElementById('tickets') as HTMLElement;
    //   element.hidden=false;

    // }

    
  }

}
