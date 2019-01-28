import { TestBed, getTestBed } from '@angular/core/testing';

import { UserServiceService } from './user-service.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { RegisterDTOInterface } from 'src/app/model/RegisterDTO';
import { EditDtoInterface } from 'src/app/model/EditProfileDto';

describe('UserListServiceService', () => {
  let injector : TestBed;
  let service : UserServiceService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientTestingModule],
      providers : [UserServiceService]
    });

    injector = getTestBed();
    service = injector.get(UserServiceService);
    httpMock = injector.get(HttpTestingController);
  });

  it('should be created', () => {
    const service:UserServiceService = TestBed.get(UserServiceService);
    expect(service).toBeTruthy();
  });


  describe('#addUser', () => {
    it('shoould be return an Observable<RegisterDTOInterface>', () => {
      const dummyUser : RegisterDTOInterface =  {
        name: 'Jelena',
        personalNo: '1111111111111',
        email: 'jelenaj@gmail.com',
        username: 'jeca',
        password: 'jeca',
        role: 'PASSENGER',
        address: {
          street: 'Boska Buhe 10',
          zip: 21000,
          city: 'Novi Sad'
        
      }
      };

      service.addUser(dummyUser).subscribe(
        pricelist => {
          expect(pricelist.name).toEqual(dummyUser.name);
        }
      );

      const req = httpMock.expectOne(`${service.Url}/create`);
      expect(req.request.method).toBe('POST');
    });
  });

  describe('#editProfile', () => {
    it('should be return an Observable<String>', () => {
      
      let p : EditDtoInterface = {
        name: 'Jelena',
        email: 'jelenapejicic2008@hotmail.com',
        username: 'jecka',
        password: 'jecka',
        address: {
          street: 'Boska Buhe 10',
          zip: 21000,
          city: 'Novi Sad'
        }
       
      };

      service.editProfile(p).subscribe(
        res => {
          expect(res.name).toBe(res.name);
        }
      );

      const req = httpMock.expectOne(`${service.Url}/profileUpdate`);
      expect(req.request.method).toBe('PUT');
    })
  })


  describe('#editProfile', () => {
    it('should be return an Observable<String>', () => {
      
      let p : EditDtoInterface = {
        name: 'Jelena',
        email: 'jelenapejicic2008@hotmail.com',
        username: 'jecka',
        password: 'jecka',
        address: {
          street: 'Boska Buhe 10',
          zip: 21000,
          city: 'Novi Sad'
        }
       
      };

      service.editProfile(p).subscribe(
        res => {
          expect(res.name).toBe(res.name);
        }
      );

      const req = httpMock.expectOne(`${service.Url}/getUser`);
      expect(req.request.method).toBe('GET');
    })
  })

});