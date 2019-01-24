import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTimetableComponent } from './search-timetable.component';

describe('SearchTimetableComponent', () => {
  let component: SearchTimetableComponent;
  let fixture: ComponentFixture<SearchTimetableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchTimetableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchTimetableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
