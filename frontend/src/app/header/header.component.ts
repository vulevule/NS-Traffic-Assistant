import { Component, OnInit, Input } from '@angular/core';
import { User } from '../model/User';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginPageComponent } from '../pages/login-page/login-page.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', './general.scss']
})
export class HeaderComponent implements OnInit {

  loggedUser: User;


  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private modalService: NgbModal) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));

    var login : HTMLElement =  document.getElementById('loginItem');
    var logout: HTMLElement = document.getElementById('logoutItem');
    var register : HTMLElement = document.getElementById('registerItem');
    var edit: HTMLElement = document.getElementById('editItem');


    if (this.loggedUser === null) {
      //alert('niko nije ulogovan');
      login.hidden = false;
      register.hidden = false;
      logout.hidden = true;
      edit.hidden = true;
    } else {
      logout.hidden = false;
      edit.hidden = false;
      login.hidden = true;
      if (this.loggedUser.role === 'INSPECTOR'){
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


  logout(){
    alert('logout');
    localStorage.removeItem('currentUser');
  }

}
export class NgbdModalContent implements OnInit {

  @Input() title = `Information`;

  constructor(
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit() {
  }

}
