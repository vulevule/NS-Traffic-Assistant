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

  @Input()
  loggedUser: UserDTO; //ovde nam je bitna samo uloga da bi mu prikazali odredjene funkcionalnosti 

  constructor(private router:Router) { }

  ngOnInit() {
  }

}
