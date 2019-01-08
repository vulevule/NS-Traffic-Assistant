import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/model/User';
import { LoggedUserService } from 'src/app/services/loggedUserService';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  @Input()
  loggedUser: User;

  constructor(private loggedUserService: LoggedUserService) { }

  ngOnInit() {
    this.loggedUser = this.loggedUserService.loggedUser;
    // Ubaciti proveru ako nije ulogovan (loggedUser je undefined) redirektovati na login stranicu
    // na logoutu postaviti loggedUser na undefined
  }

}
