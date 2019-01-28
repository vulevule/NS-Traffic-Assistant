import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayTimetableComponent } from './display-timetable.component';

describe('DisplayTimetableComponent', () => {
  let component: DisplayTimetableComponent;
  let fixture: ComponentFixture<DisplayTimetableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DisplayTimetableComponent]
    })
    fixture = TestBed.createComponent(DisplayTimetableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.displayItems = [{
      mark: '1A', name: 'linija test', times: []
    }]
  }));


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
