import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTimetableComponent } from './create-timetable.component';

describe('CreateTimetableComponent', () => {
  let component: CreateTimetableComponent;
  let fixture: ComponentFixture<CreateTimetableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTimetableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTimetableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
