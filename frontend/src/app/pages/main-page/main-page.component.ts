import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/model/User';
import { LoggedUserService } from 'src/app/services/loggedUserService';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/model/UserDTO';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css', './general.scss']
})
export class MainPageComponent implements OnInit {

  @Input()
  loggedUser: UserDTO;

  constructor(private loggedUserService: LoggedUserService, private router: Router) { }

  ngOnInit() {
    
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    // Ubaciti proveru ako nije ulogovan (loggedUser je undefined) redirektovati na login stranicu
    // na logoutu postaviti loggedUser na undefined

     if(!this.loggedUser) {
      //alert("No logged user\nI will redirect you to login page in 3 seconds");
      var router = this.router;
      //setTimeout(function() {
        var element=document.getElementById('tickets') as HTMLElement;
        element.hidden=true;
        var element1=document.getElementById('editItem') as HTMLElement;
        element.hidden=true;
        var element2=document.getElementById('loginItem') as HTMLElement;
        element.hidden=false;
        var element2=document.getElementById('registerItem') as HTMLElement;
        element.hidden=false;
        router.navigate(["/main"]);
      //}, 3000, router);
    } 
    else{
      var element=document.getElementById('tickets') as HTMLElement;
      element.hidden=false;

    }

    
  }

}
