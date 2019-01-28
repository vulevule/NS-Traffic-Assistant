import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyTicketFormComponent } from './buy-ticket-form.component';
import { Observable, of } from 'rxjs';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { DebugElement, Component, Input } from '@angular/core';
import { By, BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DisplayTicketComponent } from '../display-ticket/display-ticket.component';
import { TicketInterface } from 'src/app/model/Ticket';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-display-ticket',
  template: '<p>Mock Display Ticket Component</p>'
})
class MockDisplayTicketCompnent {
  @Input() role: String;
  @Input() ticket: TicketInterface;
}

describe('BuyTicketFormComponent', () => {
  let component: BuyTicketFormComponent;
  let fixture: ComponentFixture<BuyTicketFormComponent>;
  let ticketService: any;

  beforeEach(() => {
    const ticketServiceMock = {
      getPrice: jasmine.createSpy('getPrice')
        .and.returnValue(of("12000")),
      buyTicket: jasmine.createSpy('buyTicket')
        .and.returnValue(of({
          id: 4,
          trafficType: 'BUS', 
          timeType: 'DAILY', trafficZone: 'SECOND', price: 100,
          userType: 'HANDICAP', serialNo: 'BMMS1231558565'
        }))
    };



    TestBed.configureTestingModule({
      declarations: [BuyTicketFormComponent, MockDisplayTicketCompnent],
      imports: [
        BrowserModule, FormsModule, NgbModule
      ],
      providers: [{
        provide: TicketServiceService,
        useValue: ticketServiceMock
      }]
    });

    fixture = TestBed.createComponent(BuyTicketFormComponent);
    component = fixture.componentInstance;
    ticketService = TestBed.get(TicketServiceService);
    fixture.detectChanges();
    component.role = 'PASSENGER';
    component.infoType = 'success';
  });


  it('should be create', () => {
    expect(component).toBeTruthy();
  });

  it('should be fetch the price on init', async(() => {
    component.ngOnInit();
    expect(ticketService.getPrice).toHaveBeenCalled();
    expect(component.time).toBe('ANNUAL');
    expect(component.type).toBe('BUS');
    expect(component.zone).toBe('FIRST');
    expect(component.buyTicket.price).toBe(12000);

  }
  ));

  it('should be fetch data in ticket when buy ticket', () => {
    component.buy();
    expect(ticketService.buyTicket).toHaveBeenCalled();
    expect(component.ticket.serialNo).toBe("BMMS1231558565");
    expect(component.ticket.trafficType).toBe("BUS");
    expect(component.ticket.timeType).toBe('DAILY');
    expect(component.ticket.trafficZone).toBe("SECOND");
  })
});
