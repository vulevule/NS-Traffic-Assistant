import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTimetableComponent } from './create-timetable.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { of } from 'rxjs';
import { TimetableService } from 'src/app/services/timetable/timetable.service';
import { LineService } from 'src/app/services/lines/line.service';

describe('CreateTimetableComponent', () => {
  let component: CreateTimetableComponent;
  let fixture: ComponentFixture<CreateTimetableComponent>;
  let timetableService: any;
  let lineService: any;

  beforeEach(() => {
    const timetableServiceMock = {
      createTimetable: jasmine.createSpy('createTimetable')
        .and.returnValue(of('Success'))
    };

    const lineServiceMock = {
      getAll: jasmine.createSpy('getAll')
        .and.returnValue(of({}))
    }

    TestBed.configureTestingModule({
      declarations: [CreateTimetableComponent],
      imports: [BrowserModule, FormsModule, NgbModule],
      providers: [{
        provide: TimetableService,
        useValue: timetableServiceMock
      },
      {
        provide: LineService,
        useValue: lineServiceMock
      }]
    });
    fixture = TestBed.createComponent(CreateTimetableComponent);
    component = fixture.componentInstance;
    timetableService = TestBed.get(TimetableService);
    lineService = TestBed.get(LineService);
    fixture.detectChanges();
    component.infoType = 'succes';
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch data in line on init', () => {
    component.ngOnInit();
    expect(lineService.getAll).toHaveBeenCalled();
  });
  it('should fetch data in message when create timetable', () => {
    component.save();
    expect(timetableService.createTimetable).toHaveBeenCalled();
  })
});
