import { TestBed, async, inject } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import {HttpModule} from '@angular/http';
import { AuthenticationService } from '../services/authentication.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

describe('AuthGuard', () => {
  let authGuard: AuthGuard;
  let authenticationService:AuthenticationService;
  let router={navigate:jasmine.createSpy('navigate')};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[HttpModule,CommonModule,FormsModule],
      providers: [AuthGuard,AuthenticationService,
      {provide: Router,useValue:router}]
    })
    .compileComponents();
  }));
  beforeEach(()=>{
    authGuard=TestBed.get(AuthGuard);
    authenticationService=TestBed.get(AuthenticationService);
  })

  it('should be able to hit route when user is logged in', inject([AuthGuard], (guard: AuthGuard) => {
    expect(guard).toBeTruthy();
  }));


  it('should not be able to hit route when user is not logged in', inject([AuthGuard], (guard: AuthGuard) => {
    expect(guard).toBeFalsy();
    expect(authGuard.canActivate()).toBe(false);

  }));
});
