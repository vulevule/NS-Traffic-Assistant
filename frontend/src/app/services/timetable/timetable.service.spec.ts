import { TestBed, getTestBed } from '@angular/core/testing';

import { TimetableService } from './timetable.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { TimetableItemInterface, TimetableDtoInterface } from 'src/app/model/Timetable';

describe('TimetableService', () => {
  let injector: TestBed;
  let service: TimetableService;
  let httpMock: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TimetableService]
    });
    injector = getTestBed();
    service = injector.get(TimetableService);
    httpMock = injector.get(HttpTestingController);

  });

  it('should be created', () => {
    const service: TimetableService = TestBed.get(TimetableService);
    expect(service).toBeTruthy();
  });

  describe('#getAllTimetableByZoneAndTrafficType', () => {
    it('should be return an Observable<TimetableItemInterface[]>', () => {
      const dummyTimetableItem: TimetableItemInterface[] = [
        { id: 1, line_name: "ZELEZNICKA - NOVO NASELJE - ZELEZNICKA", line_mark: "1A", timetable_id: 10, workdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], sundayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], saturdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }] },
        { id: 2, line_name: "PETROVARADIN - NOVO NASELJE - ZELEZNICKA", line_mark: "10A", timetable_id: 10, workdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], sundayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], saturdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }] },
        { id: 3, line_name: "ZELEZNICKA - NOVO NASELJE - PETROVARADIN", line_mark: "10B", timetable_id: 10, workdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], sundayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }], saturdayTimes: [{ hours: 10, minutes: 30 }, { hours: 15, minutes: 45 }, { hours: 16, minutes: 45 }] }];

      let zone = "first";
      let type = "bus";
      service.getAllTimetableByZoneAndTrafficType(zone, type).subscribe(
        items => {
          expect(items.length).toBe(3);
          expect(items).toEqual(dummyTimetableItem);
        }
      );

      const req = httpMock.expectOne(`${service.timetableUrl}/getItemByTrafficTypeAndZone?zone=${zone}&type=${type}`);
      expect(req.request.method).toBe('GET');

    })
  });

  describe('#addTimetable', () => {
    it('should be return an Observable<String>', () => {
      const dummyMessage = "The timetable has been successfully added!";
      let timetable: TimetableDtoInterface = {
        timetables: [
          { line_mark: '1A', line_name: 'ZELEZNICKA - NOVO NASELJE - ZELEZNICKA', line_type: 'BUS', workdayTimes: "15:30, 16:17,18:49", saturdayTimes: "15:30, 16:17,18:49", sundayTimes: "15:30, 16:17,18:49" }
        ]
      };
      service.createTimetable(timetable).subscribe(
        data => {
          expect(data).toBe(dummyMessage);
        }
      )

      const req = httpMock.expectOne(`${service.timetableUrl}/addTimetable`);
      expect(req.request.method).toBe('POST');
    });
  });


});
