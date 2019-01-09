import { Component, OnInit, Input } from '@angular/core';
import { User } from '../model/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', './general.scss']
})
export class HeaderComponent implements OnInit {

  @Input()
  loggedUser: User;

  constructor() { }

  ngOnInit() {
  }

}
