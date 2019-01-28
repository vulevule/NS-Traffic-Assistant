import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UseCheckTicketComponent } from './use-check-ticket.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { TicketServiceService } from 'src/app/services/ticket/ticket-service.service';
import { LineService } from 'src/app/services/lines/line.service';

describe('UseCheckTicketComponent', () => {
  let component: UseCheckTicketComponent;
  let fixture: ComponentFixture<UseCheckTicketComponent>;
  let ticketService: any;
  let lineService: any;
  beforeEach(() => {
    const ticketServiceMock = {
      useTicket: jasmine.createSpy('useTicket')
      .and.returnValue(of(""))
    }
    const lineServiceMock = {
      getAll: jasmine.createSpy('getAll')
      .and.returnValue(of({}))
    }
    TestBed.configureTestingModule({
      
      declarations: [UseCheckTicketComponent],
      imports: [NgbModal],
      providers: [
        {
          provide: TicketServiceService, useValue: ticketServiceMock
        },
        {
          provide : LineService, useValue: lineServiceMock
        }
      ]
    })


    fixture = TestBed.createComponent(UseCheckTicketComponent);
    component = fixture.componentInstance;
    lineService = TestBed.get(LineService);
    ticketService = TestBed.get(TicketServiceService);
    fixture.detectChanges();
  });



  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch data in line - ngOnInit', ()=>{
    component.ngOnInit();
    expect(lineService.getAll).toHaveBeenCalled();
  });


});
