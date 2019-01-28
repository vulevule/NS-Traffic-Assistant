import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayPriceListComponent } from './display-price-list.component';
import { PriceListServiceService } from 'src/app/services/pricelist/price-list-service.service';
import { PriceListInterface } from 'src/app/model/Pricelist';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { FilterByTrafficTypePipe } from 'src/app/ticket/pipes/filter-by-traffic-type.pipe';
import { FilterItemByTrafficTypePipe } from './pipes/filter-item-by-traffic-type.pipe';
import { FilterItemByTicketTimePipe } from './pipes/filter-item-by-ticket-time.pipe';
import { FilterItemByZonePipe } from './pipes/filter-item-by-zone.pipe';

describe('DisplayPriceListComponent', () => {
  let component: DisplayPriceListComponent;
  let fixture: ComponentFixture<DisplayPriceListComponent>;
  let pricelistService: any;

  beforeEach(async(() => {
    let p: PriceListInterface = {
      items: [{
        price: 100, trafficType: 'bus', timeType: 'single', zone: 'second',
        studentDiscount: 0, handycapDiscount: 0, seniorDiscount: 0
      },
      {
        price: 12000, trafficType: 'metro', timeType: 'annual', zone: 'first',
        studentDiscount: 10, handycapDiscount: 5, seniorDiscount: 5
      }
      ]
    }
    let priceListServiceMocked = {
      getPricelist: jasmine.createSpy('getPricelist')
        .and.returnValue(of(p))
    }
    TestBed.configureTestingModule({
      declarations: [DisplayPriceListComponent,
        FilterItemByTrafficTypePipe,
        FilterItemByTicketTimePipe,
        FilterItemByZonePipe],
      imports: [
        FormsModule


      ],
      providers: [
        { provide: PriceListServiceService, useValue: priceListServiceMocked }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayPriceListComponent);
    component = fixture.componentInstance;
    pricelistService = TestBed.get(PriceListServiceService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch the pricelist on inti', () => {
    component.ngOnInit();
    expect(pricelistService.getPricelist).toHaveBeenCalled();

    fixture.whenStable()
      .then(() => {
        expect(component.pricelist.items.length).toBe(2);
        fixture.detectChanges();
        const table = fixture.debugElement.query(By.css('table'));
        expect(table.nativeElement.rows.length).toBe(3);
      })
  })
});
