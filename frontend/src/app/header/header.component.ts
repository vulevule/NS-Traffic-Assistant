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

  @Input()
  loggedUser: User;

  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private modalService: NgbModal) {}

  ngOnInit() {
  }

  open() {
    const modalRef = this.modalService.open(LoginPageComponent);
    modalRef.componentInstance.name = 'Login';
  }
 

}
export class NgbdModalContent implements OnInit {

    @Input() title = `Information`;
  
    constructor(
      public activeModal: NgbActiveModal
    ) {}
  
    ngOnInit() {
    }

  }
