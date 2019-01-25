import { Component, OnInit } from '@angular/core';
import { PriceListInterface } from 'src/app/model/Pricelist';
import { PriceListServiceService } from 'src/app/services/pricelist/price-list-service.service';
import { ItemInterface } from 'src/app/model/PriceItem';

@Component({
  selector: 'app-display-price-list',
  templateUrl: './display-price-list.component.html',
  styleUrls: ['./display-price-list.component.css', './general.scss']
})
export class DisplayPriceListComponent implements OnInit {
  pricelist: PriceListInterface;
  items : ItemInterface[];
  message : string = '';
  infoType : string;

  displayType = {
    bus: false,
    tram: false,
    metro: false
  };

  displayZone = {
    first: false,
    second: false
  };

  displayTicketTime = {
    annual : false,
    month : false,
    single : false,
    daily : false
  }

  constructor(private pricelistService: PriceListServiceService) { }

  ngOnInit() {
    this.pricelistService.getPricelist()
      .subscribe( (data : PriceListInterface) => {
          this.pricelist = data;
          this.items = this.pricelist.items;
      },
      error => {
        this.message =  "There is no active price list!";
        this.infoType = 'danger';
      }
      );
  }

  reload(){
    alert('reload');
    this.items = this.pricelist.items;
  }

}
