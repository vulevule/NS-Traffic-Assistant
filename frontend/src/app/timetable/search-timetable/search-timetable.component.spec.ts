import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTimetableComponent } from './search-timetable.component';
import { NgForm, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';

describe('SearchTimetableComponent', () => {
  let component: SearchTimetableComponent;
  let fixture: ComponentFixture<SearchTimetableComponent>;

  beforeEach((() => {
    TestBed.configureTestingModule({
      declarations: [SearchTimetableComponent],
      imports: [BrowserModule, FormsModule, NgbModule]
    });
    fixture = TestBed.createComponent(SearchTimetableComponent);
    component = fixture.componentInstance;
    
    fixture.detectChanges();
  }));


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
