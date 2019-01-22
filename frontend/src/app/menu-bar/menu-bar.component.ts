import { Component, Input, OnInit } from '@angular/core';
import { User } from '../model/User';
import { Router } from '@angular/router';
import { UserDTO } from '../model/UserDTO';


@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.css', './general.scss']
})
export class MenuBarComponent implements OnInit {

  loggedUser: UserDTO; //ovde nam je bitna samo uloga da bi mu prikazali odredjene funkcionalnosti 

  constructor(private router: Router) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    //uzmemo sa stranice sve elemente koje treba da sakrijemo 
    var station: HTMLElement = document.getElementById('stations');
    var lines: HTMLElement = document.getElementById('lines');
    var tickets: HTMLElement = document.getElementById('tickets');
    var pricelist: HTMLElement = document.getElementById('pricelist');
    var report: HTMLElement = document.getElementById('report');

    if (this.loggedUser !== null) {
      if (this.loggedUser.role === 'PASSENGER') {
        tickets.hidden = false;
        pricelist.hidden = false;
        report.hidden = true;
        lines.hidden = false;
        station.hidden = false;
      } else if (this.loggedUser.role === 'INSPECTOR') {
        tickets.hidden = false;
        pricelist.hidden = false;
        report.hidden = true;
        lines.hidden = false;
        station.hidden = false;
      } else if (this.loggedUser.role === 'ADMIN') {
        tickets.hidden = true;
        pricelist.hidden = false;
        report.hidden = false;
        lines.hidden = false;
        station.hidden = false;

      }
    }else{
        tickets.hidden = true;
        pricelist.hidden = false;
        report.hidden = true;
        lines.hidden = false;
        station.hidden = false;
    }

  }

}
