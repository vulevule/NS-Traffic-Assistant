import { Component, OnInit } from '@angular/core';
import { UserDTO } from 'src/app/model/UserDTO';



@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css', './general.scss']
})
export class TicketComponent implements OnInit {

  //ovde cemo uzeti role-u korisnika 
  role : String ='';

  constructor() { 
    let user : UserDTO = JSON.parse(
      localStorage.getItem('currentUser'));

    if(user !== null){
      this.role = user.role;
    }

  }

  ngOnInit() {
  }


}
