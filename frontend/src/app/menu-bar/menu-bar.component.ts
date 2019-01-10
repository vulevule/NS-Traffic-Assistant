import { Component, Input, OnInit } from '@angular/core';
import { User } from '../model/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.css', './general.scss']
})
export class MenuBarComponent implements OnInit {

  @Input()
  loggedUser: User;

  constructor(private router:Router) { }

  ngOnInit() {
  }

}
