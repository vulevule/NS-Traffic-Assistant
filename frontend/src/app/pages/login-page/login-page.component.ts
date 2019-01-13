import { Component, OnInit, Input ,ViewEncapsulation} from '@angular/core';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {Observable} from 'rxjs';
import { throwError } from 'rxjs';
import{Router} from '@angular/router'

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginPageComponent implements OnInit{

  public user;
  public wrongUsernameOrPass:boolean;

  constructor(
    private AuthenticationService:AuthenticationService,private router:Router)
  {
    this.user={};
    this.wrongUsernameOrPass=false;
}
ngOnInit(){}
login():void{
  this.AuthenticationService.login(this.user.username,this.user.password).subscribe((loggedIn:boolean)=>{
    console.log("loggedIn");
    if(loggedIn){
      console.log(loggedIn);
      this.router.navigate(['/main']);
    }
  },(err:Error)=>{
    if(err.toString()==='Illegal login'){
      this.wrongUsernameOrPass=true;
      console.log(err);
    }else{
      throwError(err);
    }

  
  })

}

}
