import { TestBed } from '@angular/core/testing';

import { PriceListServiceService } from './price-list-service.service';

describe('PriceListServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PriceListServiceService = TestBed.get(PriceListServiceService);
    expect(service).toBeTruthy();
  });
});
