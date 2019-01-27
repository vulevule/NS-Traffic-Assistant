import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/model/User';
import { UserServiceService } from 'src/app/services/user/user-service.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
   user:User;
   public u;
   message: String = '';
  type = '';
  constructor(public activeModal: NgbActiveModal,private userSer:UserServiceService) { }
  
  ngOnInit() {
    this.u={};
    this.userSer.getUser().subscribe(data=>{
      this.user=data;
      this.type="success";
      this.message="Your changes are saved."
    },error=>{
      this.type="danger";
      this.message="Something went wrong. Please try again."


    });
  }

}
