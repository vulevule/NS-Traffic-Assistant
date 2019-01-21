import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UseCheckTicketComponent } from './use-check-ticket.component';

describe('UseCheckTicketComponent', () => {
  let component: UseCheckTicketComponent;
  let fixture: ComponentFixture<UseCheckTicketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UseCheckTicketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UseCheckTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
