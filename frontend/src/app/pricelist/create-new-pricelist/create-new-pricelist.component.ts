import { Component, OnInit } from '@angular/core';
import { ItemInterface } from 'src/app/model/PriceItem';
import { forEach } from '@angular/router/src/utils/collection';
import { PriceListInterface } from 'src/app/model/Pricelist';
import { PriceListServiceService } from 'src/app/services/pricelist/price-list-service.service';

@Component({
  selector: 'app-create-new-pricelist',
  templateUrl: './create-new-pricelist.component.html',
  styleUrls: ['./create-new-pricelist.component.css']
})
export class CreateNewPricelistComponent implements OnInit {

  items: ItemInterface[] = [];

  pricelist : PriceListInterface;

  message : string = '';
  infoType : string;

  type: string[] = ['BUS', 'METRO', 'TRAM'];
  zone: string[] = ['FIRST', 'SECOND'];
  time: string[] = ['ANNUAL', 'MONTH', 'DAILY', 'SINGLE'];

  constructor(private pricelistService : PriceListServiceService) {
   

  }

  ngOnInit() {
    this.items = [];
    this.type.forEach(type => {
      this.zone.forEach(zone => {
        this.time.forEach(time => {
          var item = {
            trafficType: type,
            zone: zone,
            timeType: time,
            price: 0,
            studentDiscount: 0,
            handycapDiscount: 0,
            seniorDiscount: 0
          };
          this.items.push(item);
        });
      });
    });

  }

  save() {
    this.pricelist = {items : this.items};
    this.pricelistService.addPricelist(this.pricelist)
      .subscribe(
        data => {
          this.message = data as string;
          this.infoType = 'success';
        }, 
        error => {
          this.message =  error.error;
          this.infoType = 'danger';
        }
      )
  }

}
