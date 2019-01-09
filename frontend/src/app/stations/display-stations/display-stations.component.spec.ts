import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayStationsComponent } from './display-stations.component';

describe('DisplayStationsComponent', () => {
  let component: DisplayStationsComponent;
  let fixture: ComponentFixture<DisplayStationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayStationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayStationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
