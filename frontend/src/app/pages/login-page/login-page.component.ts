import { Component, OnInit, Input } from '@angular/core';
import { LoggedUserService } from 'src/app/services/loggedUserService';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  @Input()
  loggedUser: User;

  constructor(private loggedUserService: LoggedUserService) { }

  ngOnInit() {
    this.loggedUser = this.loggedUserService.loggedUser;
    // Ubaciti proveru ako je ulogovan (loggedUser nije undefined) redirektovati na main stranicu
    // na kraju logovanja postaviti loggedUser na tog sto se ulogovao
  }

}
