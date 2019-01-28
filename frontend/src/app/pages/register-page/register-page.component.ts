/* tslint:disable: member-ordering forin */
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators, FormBuilder } from "@angular/forms";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from 'src/app/services/authentication.service'
import { matchingPasswordValidator } from './matching-password.directive';
import { DialogComponent } from 'src/app/user/dialog/dialog.component';
import { UserServiceService } from 'src/app/services/user/user-service.service';
import { RegisterDTOInterface } from 'src/app/model/RegisterDTO';
import { UserDTO } from 'src/app/model/UserDTO';





@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {
  loggedUser : UserDTO;
  role : String = '';
  public user;
  u: RegisterDTOInterface;
  message: String = '';
  type = '';

  constructor(public activeModal: NgbActiveModal, private modalService: NgbModal, private userService: UserServiceService, private authService: AuthenticationService) {

    this.user = {};
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    if(this.loggedUser !== null){
      this.role = this.loggedUser.role;
    }

  }

  ngOnInit() {
    this.u = {
      name: '',
      personalNo: '',
      email: '',
      username: '',
      password: '',
      role: '',
      address: {
        street: '',
        zip: 0,
        city: ''
      }
    }



  }
  register() {



if(this.loggedUser===null){
  this.u.role = "PASSENGER";

}
    
    if(this.role==="ADMIN"){

      if(this.u.role==="ADMIN" || this.u.role==="admin" || this.u.role==="Admin"){
        this.u.role="ADMIN"
      }
      else if(this.u.role==="INSPECTOR" || this.u.role==="inspector" || this.u.role==="Inspector"){
        this.u.role="INSPECTOR"
      }
      else{
        this.message='This role does not exist';
        this.type='danger;'
        
        return;}
    }

    console.log(this.u);
    this.userService.addUser(this.u)
      .subscribe(data => {
        if(this.role==="ADMIN"){
          this.message = 'Success login';
            this.type = 'success';
            this.activeModal.close();
          return;
        }
        this.authService.login(this.u.username, this.u.password).subscribe(
          data => {
            let loadingUser = data;
            let token = data.token;
            let role = data.role;

            localStorage.setItem('currentUser', JSON.stringify({ username: this.u.username, role: role, token: token }));
            //this.loading = true;
            this.message = 'Success login';
            this.type = 'success';
            //treba navigirati


            const modalRef = this.modalService.open(DialogComponent);
            this.activeModal.close();
            console.log(this.user);



          }, error => {


          });



      }, error => {

        //this.loading = false;
        this.user.username = '';
        if (error.status === 409) {
          this.message = 'Username already exist';
          this.type = 'danger';
          //alert(this.wrongUsernameOrPass)
          //alert(error.error);
        }
      }
      );






  }



}

