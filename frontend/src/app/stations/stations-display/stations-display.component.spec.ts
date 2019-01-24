import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StationsDisplayComponent } from './stations-display.component';

describe('StationsDisplayComponent', () => {
  let component: StationsDisplayComponent;
  let fixture: ComponentFixture<StationsDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StationsDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StationsDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
