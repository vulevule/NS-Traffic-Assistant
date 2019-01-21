import { Component, OnInit, Input ,ViewEncapsulation} from '@angular/core';
import {AuthenticationService} from 'src/app/services/authentication.service';

import{Router} from '@angular/router';
import { first } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

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
  public loading : string;

  constructor(
    private AuthenticationService:AuthenticationService,private router:Router)
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
  // await this.AuthenticationService.getXToken(this.user.username, this.user.password)
  // .then(data => {this.loading = data});

  var response : any;
  //await this.AuthenticationService.getXToken(this.user.username, this.user.password).subscribe(data => response = data);
  
  await this.AuthenticationService.login(this.user.username,this.user.password).subscribe((loggedIn:boolean)=>{
    console.log("loggedIn");
    if(loggedIn){
      console.log(loggedIn);
      this.dismiss();
      var currentUser = JSON.parse(
        localStorage.getItem('currentUser'));
      this.router.navigate(['http://localhost:4200/main']);
    }
    else{
      this.wrongUsernameOrPass=true;

    }
  },(err:Error)=>{
    if(err.toString()==='Illegal login'){
      this.wrongUsernameOrPass=true;
      console.log(err);
    }else{
      throwError(err);
      this.wrongUsernameOrPass=true;
    }

  
  })

  

}

}
