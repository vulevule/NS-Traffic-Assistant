import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayPriceListComponent } from './display-price-list.component';

describe('DisplayPriceListComponent', () => {
  let component: DisplayPriceListComponent;
  let fixture: ComponentFixture<DisplayPriceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayPriceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayPriceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
