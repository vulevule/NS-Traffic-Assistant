import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EditDtoInterface } from 'src/app/model/EditProfileDto';
import { UserServiceService } from 'src/app/services/user/user-service.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
   user: EditDtoInterface;
   public u;
   message: String = '';
  type = '';
  constructor(public activeModal: NgbActiveModal,private userSer:UserServiceService) { }
  
  ngOnInit() {
    this.u={};
    this.user = {
      name: '',
      email: '',
      username: '',
      password: '',
      address: {
        street: '',
        zip: 0,
        city: ''
      }}
    this.userSer.getUser().subscribe(data=>{
      this.user=data;
      this.user.password='';
      this.u.repeatPassword='';
      
    },error=>{
      this.type="danger";
      this.message="Something went wrong.Please try again.";


    });
  }

  save(){

    this.userSer.editProfile(this.user).subscribe(data=>{
      this.type="success";
      this.message="Your changes are saved."
      this.activeModal.close();

    },error=>{

if(error.status===200){
  this.type="success";
      this.message="Your changes are saved."
      this.activeModal.close();


}else{

      
      this.type="danger";
this.message="Something went wrong.Please try again.";}


    })
  }

}
