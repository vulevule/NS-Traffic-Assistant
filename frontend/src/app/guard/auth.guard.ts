import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService,private router:Router){}
  canActivate() {
    if(this.authenticationService.isLoggedIn()){
      return true;
    }
    else{
      this.router.navigate(['/main']);
      return false;
    }
  }
}
