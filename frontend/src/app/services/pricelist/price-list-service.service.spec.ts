import { TestBed, getTestBed } from '@angular/core/testing';

import { PriceListServiceService } from './price-list-service.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { PriceListInterface } from 'src/app/model/Pricelist';

describe('PriceListServiceService', () => {
  let injector : TestBed;
  let service : PriceListServiceService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientTestingModule],
      providers : [PriceListServiceService]
    });

    injector = getTestBed();
    service = injector.get(PriceListServiceService);
    httpMock = injector.get(HttpTestingController);
  });

  it('should be created', () => {
    const service: PriceListServiceService = TestBed.get(PriceListServiceService);
    expect(service).toBeTruthy();
  });


  describe('#getPricelist', () => {
    it('shoould be return an Observable<PricelistInterface>', () => {
      const dummyPricelist : PriceListInterface = {
        items : [
          {price : 100, trafficType : 'bus', timeType : 'single', zone : 'second', studentDiscount : 0, handycapDiscount : 0, seniorDiscount : 0},
          {price : 12000, trafficType : 'metro', timeType : 'annual', zone : 'first', studentDiscount : 10, handycapDiscount : 5, seniorDiscount : 5}
        ]
      };

      service.getPricelist().subscribe(
        pricelist => {
          expect(pricelist.items.length).toBe(2);
          expect(pricelist).toEqual(dummyPricelist);
        }
      );

      const req = httpMock.expectOne(`${service.pricelistUrl}/getPricelist`);
      expect(req.request.method).toBe('GET');
    });
  });

  describe('#addPricelist', () => {
    it('should be return an Observable<String>', () => {
      const dummyMessage = "The price list has been successfully added!";
      let p : PriceListInterface = {
        items : [
          {price : 100, trafficType : 'bus', timeType : 'single', zone : 'second', studentDiscount : 0, handycapDiscount : 0, seniorDiscount : 0},
          {price : 12000, trafficType : 'metro', timeType : 'annual', zone : 'first', studentDiscount : 10, handycapDiscount : 5, seniorDiscount : 5}
        ]
      };

      service.addPricelist(p).subscribe(
        res => {
          expect(res).toBe(dummyMessage);
        }
      );

      const req = httpMock.expectOne(`${service.pricelistUrl}/addPricelist`);
      expect(req.request.method).toBe('POST');
    })
  })

});
