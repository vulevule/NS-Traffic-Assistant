import { Component, OnInit, Input } from '@angular/core';
import { User } from '../model/User';
import {AuthenticationService} from 'src/app/services/authentication.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginPageComponent } from '../pages/login-page/login-page.component';
import { RegisterPageComponent } from '../pages/register-page/register-page.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', './general.scss']
})
export class HeaderComponent implements OnInit {

  loggedUser: User;


  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private modalService: NgbModal, private AuthenticationService:AuthenticationService ) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));

    var login : HTMLElement =  document.getElementById('loginItem');
    var logout: HTMLElement = document.getElementById('logoutItem');
    var register : HTMLElement = document.getElementById('registerItem');
    var edit: HTMLElement = document.getElementById('editItem');


    if (this.loggedUser === null) {
      alert('niko nije ulogovan');
      login.hidden = false;
      register.hidden = false;
      logout.hidden = true;
      edit.hidden = true;
    } else {
      logout.hidden = false;
      edit.hidden = false;
      login.hidden = true;
      if (this.loggedUser.role === 'ADMIN'){
        register.hidden = false;
      }else{
        register.hidden = true;
      }

    }

  }

  open() {
    const modalRef = this.modalService.open(LoginPageComponent);
    modalRef.componentInstance.name = 'Login';
  }
 openReg(){

  const modalRef = this.modalService.open(RegisterPageComponent);
    //modalRef.componentInstance.name = 'Login';
 }

  logout(){
    this.AuthenticationService.logout();
    location.reload();
  }

}



