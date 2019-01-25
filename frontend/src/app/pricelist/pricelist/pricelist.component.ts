import { Component, OnInit } from '@angular/core';
import { UserDTO } from 'src/app/model/UserDTO';
import { WebDriverLogger } from 'blocking-proxy/built/lib/webdriver_logger';

@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css', './general.scss']
})
export class PricelistComponent implements OnInit {


  loggedUser : UserDTO;
  role : String;
  

  constructor() { 
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    this.role = this.loggedUser.role;
  }

  ngOnInit() {
    //dobavimo elemente na stranici
    

  }

}
