import { Component, OnInit } from '@angular/core';
import { PriceListReaderDto } from 'src/app/model/Pricelist';
import { PriceListServiceService } from 'src/app/services/pricelist/price-list-service.service';
import { Item } from 'src/app/model/PriceItem';

@Component({
  selector: 'app-display-price-list',
  templateUrl: './display-price-list.component.html',
  styleUrls: ['./display-price-list.component.css', './general.scss']
})
export class DisplayPriceListComponent implements OnInit {
  pricelist: PriceListReaderDto;
  items : Item[];

  constructor(private pricelistService: PriceListServiceService) { }

  async ngOnInit() {
    // await this.pricelistService.getPricelist()
    //   .then(data => { this.pricelist = data });
    // this.items = this.pricelist.items;
  }

}
