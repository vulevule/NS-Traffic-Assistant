import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TicketServiceService } from './ticket-service.service';

describe('TicketServiceService', () => {

  let injector: TestBed;
  let service: TicketServiceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TicketServiceService]
    });

    injector = getTestBed();
    service = injector.get(TicketServiceService);
    httpMock = injector.get(HttpTestingController);

  });

  it('should be created', () => {
    const service: TicketServiceService = TestBed.get(TicketServiceService);
    expect(service).toBeTruthy();
  });


  describe('#getAllTicket', () => {
    it('should be return an Observable<TicketInterface[]>', () => {
      const dummyTickets = [
        { id: 1, trafficType: 'BUS', timeType: 'MONTH', trafficZone: 'FIRST', price: 900, userType: 'STUDENT', serialNo: 'BMMS1231555' },
        { id: 2, trafficType: 'METRO', timeType: 'ANNUAL', trafficZone: 'SECOND', price: 1000, userType: 'REGULAR', serialNo: 'BMMS12315565' },
        { id: 3, trafficType: 'TRAM', timeType: 'SINGLE', trafficZone: 'FIRST', price: 120, userType: 'SENIOR', serialNo: 'BMMS1231554555' },
        { id: 4, trafficType: 'BUS', timeType: 'DAILY', trafficZone: 'SECOND', price: 100, userType: 'HANDICAP', serialNo: 'BMMS1231558565' }
      ];

      service.getAllTickets().subscribe(
        tickets => {
          expect(tickets.length).toBe(4);
          expect(tickets).toEqual(dummyTickets);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/all`);
      expect(req.request.method).toBe('GET');
      req.flush(dummyTickets);

    })
  });


  describe('#myTicket', () => {
    it('should be return an Observable<TicketInterface[]>', () => {
      const dummyTickets = [
        { id: 1, trafficType: 'BUS', timeType: 'MONTH', trafficZone: 'FIRST', price: 900, userType: 'STUDENT', serialNo: 'BMMS1231555' },
        { id: 2, trafficType: 'METRO', timeType: 'ANNUAL', trafficZone: 'SECOND', price: 1000, userType: 'REGULAR', serialNo: 'BMMS12315565' },
        { id: 3, trafficType: 'TRAM', timeType: 'SINGLE', trafficZone: 'FIRST', price: 120, userType: 'SENIOR', serialNo: 'BMMS1231554555' },
        { id: 4, trafficType: 'BUS', timeType: 'DAILY', trafficZone: 'SECOND', price: 100, userType: 'HANDICAP', serialNo: 'BMMS1231558565' }
      ];

      service.getMyTicket().subscribe(
        tickets => {
          expect(tickets.length).toBe(4);
          expect(tickets).toEqual(dummyTickets);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/myTicket`);
      expect(req.request.method).toBe('GET');
      req.flush(dummyTickets);
    })
  });

  describe('#buyTicket', () => {
    it('should be return an Observable<TicketInterface>', () => {
      const dummyTicket = { id: 1, trafficType: 'BUS', timeType: 'MONTH', trafficZone: 'FIRST', price: 900, userType: 'STUDENT', serialNo: 'BMMS1231555' };
      let t = { trafficType: 'BUS', timeType: 'ANNUAL', trafficZone: 'FIRST' };
      service.buyTicket(t).subscribe(
        data => {
          expect(data).toEqual(dummyTicket);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/buyTicket`);
      expect(req.request.method).toBe('POST');
      req.flush(dummyTicket);
    })
  });

  describe('#useTicket', () => {
    it('should be return an Observable<String>', () => {
      const result = "The ticket was successfully used";

      let sno = "BMSF254545";
      let line = 1;
      service.useTicket(sno, line).subscribe(
        data => {
          expect(data).toBe(result)
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/useTicket?serialNo=${sno}&line=${line}`);
      expect(req.request.method).toBe('GET');
    });
  });


  describe('#checkTicket', () => {
    it('should be return an Observable<String>', () => {
      const result = "The ticket was successfully checked";
      let sno = "BMSF254545";
      let line = 1;

      service.checkTicket(sno, line).subscribe(
        data => {
          expect(data).toBe(result);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/checkTicket?serialNo=${sno}&line=${line}`);
      expect(req.request.method).toBe('GET');

    });
  });

  describe('#getPrice', () => {
    it('should be return an Observable<String>', () => {
      const result = "950";
      let t = { trafficType: 'BUS', timeType: 'ANNUAL', trafficZone: 'FIRST' };

      service.getPrice(t).subscribe(
        data => {
          expect(data).toBe(result);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/price?type=BUS&zone=FIRST&time=ANNUAL`);
      expect(req.request.method).toBe('GET');
    });
  });

  describe('#getReport', () => {
    it('should be return an Observable<ReportInterface>', () => {
      const dummyReport = {
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
      let month = 1;
      let year = 2019;
      let type = "month";

      service.getReport(month, year, type).subscribe(
        data => {
          expect(data).toEqual(dummyReport);
        }
      );

      const req = httpMock.expectOne(`${service.ticketUrl}/report?month=${month}&year=${year}&type=${type}`);
      expect(req.request.method).toBe('GET');
    })
  })

});


