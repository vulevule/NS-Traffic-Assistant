import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/PriceItem';
import { forEach } from '@angular/router/src/utils/collection';
import { PriceListReaderDto, PriceList } from 'src/app/model/Pricelist';
import { PriceListServiceService } from 'src/app/services/pricelist/price-list-service.service';

@Component({
  selector: 'app-create-new-pricelist',
  templateUrl: './create-new-pricelist.component.html',
  styleUrls: ['./create-new-pricelist.component.css']
})
export class CreateNewPricelistComponent implements OnInit {

  items: Item[] = [];

  pricelist : PriceListReaderDto;

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
          var item = new Item({
            trafficType: type,
            zone: zone,
            timeType: time,
            price: 0,
            studentDiscount: 0,
            handycapDiscount: 0,
            seniorDiscount: 0
          });
          this.items.push(item);
        });
      });
    });

  }

  async save() {
    //pozvati metodu iz servisa za kreiranje rasporeda
    var p = new PriceList({ items: this.items });

    this.items.forEach(element => {
      alert(element.price);
    });
  /*  await this.pricelistService.addPricelist(p)
    .then(data => { this.pricelist = data });
*/
  }

}
