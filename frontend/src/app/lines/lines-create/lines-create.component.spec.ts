import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinesCreateComponent } from './lines-create.component';

describe('LinesCreateComponent', () => {
  let component: LinesCreateComponent;
  let fixture: ComponentFixture<LinesCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinesCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinesCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
