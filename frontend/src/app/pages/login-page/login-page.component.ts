import { Component, OnInit, Input ,ViewEncapsulation} from '@angular/core';
import {AuthenticationService} from 'src/app/services/authentication.service';

import{Router} from '@angular/router';
import { first } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { LoginUserDtoInterface } from 'src/app/model/LoginUserDto';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginPageComponent implements OnInit{

  public user;
  public wrongUsernameOrPass:boolean;
  public role;
  public loading : boolean;
  public message : string = '';
  public type : string;

  constructor(
    private AuthenticationService:AuthenticationService,/*private router:Router,*/
      public activeModal: NgbActiveModal)
  {
    this.user={};
    this.wrongUsernameOrPass=false;
}
ngOnInit(){}

dismiss(){
  var element=document.getElementById('login') as HTMLElement;
  element.hidden=true;

}

async login(){
  var loadingUser : LoginUserDtoInterface;
  this.AuthenticationService.login(this.user.username, this.user.password)
    .subscribe(data => {
      loadingUser = data;
      let token = data.token;
      let role = data.role;
      localStorage.setItem('currentUser', JSON.stringify({ username: this.user.username, role:role, token: token }));
      //this.loading = true;
     this.message = 'Success login';
      this.type  = 'success';
      //treba navigirati
      location.reload();
  

    }, error => {
      //this.loading = false;
      this.user.username='';
      this.user.password='';
      this.message = 'Wrong username or password';
      this.type = 'danger';
      //alert(this.wrongUsernameOrPass)
      //alert(error.error);
    }
    );

  
  

}


}

