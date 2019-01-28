import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFormComponent } from './search-form.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FilterTicketByTicketTimePipe } from '../pipes/filter-ticket-by-ticket-time.pipe';
import { FilterByZonePipe } from 'src/app/lines/lines-display/pipes/filter-by-zone.pipe';
import { FilterTicketByZonePipe } from '../pipes/filter-ticket-by-zone.pipe';
import { FilterByTrafficTypePipe } from '../pipes/filter-by-traffic-type.pipe';
import { Component, Input } from '@angular/core';
import { TicketInterface } from 'src/app/model/Ticket';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-display-ticket',
  template: '<p>Mock Display Ticket Component</p>'
})
class MockDisplayTicket {
  @Input() role : String;
  @Input() ticket : TicketInterface;
}

describe('SearchFormComponent', () => {
  let component: SearchFormComponent;
  let fixture: ComponentFixture<SearchFormComponent>;
  let ticketService : any;
  var t : TicketInterface[] = [
    { id: 1, trafficType: 'BUS', timeType: 'MONTH', trafficZone: 'FIRST', price: 900, userType: 'STUDENT', serialNo: 'BMMS1231555' },
    { id: 2, trafficType: 'METRO', timeType: 'ANNUAL', trafficZone: 'SECOND', price: 1000, userType: 'REGULAR', serialNo: 'BMMS12315565' },
    { id: 3, trafficType: 'TRAM', timeType: 'SINGLE', trafficZone: 'FIRST', price: 120, userType: 'SENIOR', serialNo: 'BMMS1231554555' },
    { id: 4, trafficType: 'BUS', timeType: 'DAILY', trafficZone: 'SECOND', price: 100, userType: 'HANDICAP', serialNo: 'BMMS1231558565' }
  ];

  var t1 : TicketInterface[] = [
    { id: 1, trafficType: 'BUS', timeType: 'MONTH', trafficZone: 'FIRST', price: 900, userType: 'STUDENT', serialNo: 'BMMS1231555' },
    { id: 2, trafficType: 'METRO', timeType: 'ANNUAL', trafficZone: 'SECOND', price: 1000, userType: 'REGULAR', serialNo: 'BMMS12315565' }];



  let ticketServiceMock = {

    getAllTickets : jasmine.createSpy('getAllTickets')
      .and.returnValue(of(t)),
    getMyTicket : jasmine.createSpy('getMyTicket')
      .and.returnValue(of(t1))
  }
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFormComponent, FilterTicketByTicketTimePipe, FilterTicketByZonePipe, FilterByTrafficTypePipe, MockDisplayTicket ],
      imports: [
        BrowserModule, FormsModule, NgbModule
      ],
      providers : [ { provide : TicketServiceService, 
        useValue : ticketServiceMock}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.role = 'PASSENGER';
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get my ticket', () => {
    component.ngOnInit();
    expect(ticketService.getMyTicket).toHaveBeenCalled();
    expect(component.tickets.length).toBe(2);
  });

  it('should get all ticket', ()=> {
    component.role = 'INSPECTOR';
    component.getTickets();
    expect(ticketService.getAllTickets).toHaveBeenCalled();
    expect(component.tickets.length).toBe(4);
  })


});
