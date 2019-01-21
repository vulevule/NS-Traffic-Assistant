import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinesSidebarComponent } from './lines-sidebar.component';

describe('LinesSidebarComponent', () => {
  let component: LinesSidebarComponent;
  let fixture: ComponentFixture<LinesSidebarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinesSidebarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinesSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
