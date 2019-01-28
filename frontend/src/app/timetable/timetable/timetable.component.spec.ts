import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TimetableComponent } from './timetable.component';
import { Component } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';


@Component({
  selector: 'app-search-timetable',
  template: '<p> Mock serach timetable component <p>'
})
class MockSearchTimetableComponent {

}

@Component({
  selector: 'app-create-timetable',
  template: '<p> Mock serach timetable component <p>'
})
class MockCreatimetableComponent {

}

describe('TimetableComponent', () => {
  let component: TimetableComponent;
  let fixture: ComponentFixture<TimetableComponent>;
  let authService: any;
  beforeEach(() => {
    const authServiceMock = {
      getCurrentUser: jasmine.createSpy('getCurrentUser')
        .and.returnValue(of({}))
    }

    TestBed.configureTestingModule({
      declarations: [TimetableComponent,
        MockSearchTimetableComponent,
        MockCreatimetableComponent],
      imports: [NgbModule, BrowserModule, FormsModule],
      providers: [{
        provide: AuthenticationService,
        useValue : authServiceMock
      }]
    });
    fixture = TestBed.createComponent(TimetableComponent);
    component = fixture.componentInstance;
    authService = TestBed.get(AuthenticationService);
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be fetch the user on init', () => {
    component.ngOnInit();
    expect(authService.getCurrentUser).toHaveBeenCalled();
  })
});
