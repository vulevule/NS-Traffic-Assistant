import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/model/User';
import { LoggedUserService } from 'src/app/services/loggedUserService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css', './general.scss']
})
export class MainPageComponent implements OnInit {

  @Input()
  loggedUser: User;

  constructor(private loggedUserService: LoggedUserService, private router: Router) { }

  ngOnInit() {
    this.loggedUser = this.loggedUserService.loggedUser;
    // Ubaciti proveru ako nije ulogovan (loggedUser je undefined) redirektovati na login stranicu
    // na logoutu postaviti loggedUser na undefined

    /* if(!this.loggedUser) {
      alert("No logged user\nI will redirect you to login page in 3 seconds");
      var router = this.router;
      setTimeout(function() {
        router.navigate(["/login"]);
      }, 3000, router);
    } */

    
  }

}
