import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayReportComponent } from './display-report.component';
import { By } from '@angular/platform-browser';

describe('DisplayReportComponent', () => {
  let component: DisplayReportComponent;
  let fixture: ComponentFixture<DisplayReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayReportComponent);
    component = fixture.componentInstance;
    component.report = {
      numOfStudentMonthTicket: 1,
      numOfHandycapMonthTicket: 1,
      numOfSeniorMonthTicket: 2,
      numOfRegularMonthTicket: 2,

      // godisnje
      numOfStudentYearTicket: 2,
      numOfHandycapYearTicket: 2,
      numOfSeniorYearTicket: 2,
      numOfRegularYearTicket: 2,

      // single
      numOfStudentSingleTicket: 2,
      numOfHandycapSingleTicket: 2,
      numOfSeniorSingleTicket: 2,
      numOfRegularSingleTicket: 2,

      // dnevne
      numOfStudentDailyTicket: 2,
      numOfHandycapDailyTicket: 2,
      numOfSeniorDailyTicket: 2,
      numOfRegularDailyTicket: 2,

      // broj kupljenih karti za odredjeni prevoz
      numOfBusTicket: 5,
      numOfMetroTicket: 5,
      numOfTramTicket: 6,

      // zarada
      money: 256222
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should assert that data is present in table', ()=>{
    const tables = fixture.debugElement.queryAll(By.css('table'));
    expect(tables.length).toBe(2);
    expect(tables[0].nativeElement.rows.length).toBe(6);
    expect(tables[1].nativeElement.rows.length).toBe(5);
  })
});
