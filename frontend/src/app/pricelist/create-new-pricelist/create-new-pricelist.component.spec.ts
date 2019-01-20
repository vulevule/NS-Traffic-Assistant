import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewPricelistComponent } from './create-new-pricelist.component';

describe('CreateNewPricelistComponent', () => {
  let component: CreateNewPricelistComponent;
  let fixture: ComponentFixture<CreateNewPricelistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewPricelistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewPricelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
