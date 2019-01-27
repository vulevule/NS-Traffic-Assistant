import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StationsCreateComponent } from './stations-create.component';

describe('StationsCreateComponent', () => {
  let component: StationsCreateComponent;
  let fixture: ComponentFixture<StationsCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StationsCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StationsCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
