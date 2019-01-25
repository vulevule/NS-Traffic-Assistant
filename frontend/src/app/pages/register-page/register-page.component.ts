/* tslint:disable: member-ordering forin */
import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators, FormBuilder} from "@angular/forms";
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { matchingPasswordValidator } from './matching-password.directive';





@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  public user;
  
  /*registerForm = new FormGroup({
    name: new FormControl(''),
    personalNo: new FormControl(''),
    email: new FormControl(''),
    city: new FormControl(''),
    street: new FormControl(''),
    zip: new FormControl(''),
    username: new FormControl(''),
    inputPassword: new FormControl(''),
    repeatPassword: new FormControl(''),
    
    
  });*/
  constructor(public activeModal: NgbActiveModal) { 
    this.user={};
    
    
  }

  ngOnInit(){

    


  }
   register(){
console.log(this.user)
    
  }

  

}
