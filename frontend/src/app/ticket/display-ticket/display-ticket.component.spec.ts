import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayTicketComponent } from './display-ticket.component';
import { By } from '@angular/platform-browser';

describe('DisplayTicketComponent', () => {
  let component: DisplayTicketComponent;
  let fixture: ComponentFixture<DisplayTicketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayTicketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayTicketComponent);
    component = fixture.componentInstance;
    component.role = 'PASSENGER';
    component.ticket = {serialNo : "BMFS12333", trafficType : 'BUS', timeType : 'ANNUAL', trafficZone : 'FIRST',userType : 'STUDENT', expirationDate : new Date(), issueDate : new Date()};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should assert that data is present in table', () => {
    const table = fixture.debugElement.query(By.css('table'));
    expect(table.nativeElement.rows.length).toBe(4);
  })
});
