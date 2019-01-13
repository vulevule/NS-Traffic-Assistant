import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import {JwtUtilsService} from 'src/app/security/jwt-utils.service';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly loginPath='http://localhost:8080/user/login'

       constructor(private http:HttpClient,private jwtUtilsService:JwtUtilsService){}

       login (username:string, password:string):Observable<boolean>{

        var headers:HttpHeaders=new HttpHeaders({'Content-Type':'application/json'});
        return this.http.post(this.loginPath,JSON.stringify({username,password}),{headers})
        .pipe(map((res:any)=>{
                    console.log(res);
                    let token=res && res['token'];
                    if (token){
                        localStorage.setItem('currentUser',JSON.stringify({username:username,roles:this.jwtUtilsService.getRoles(token),token:token}));
                        return true;
            }
            else{
                return false;
            }
            }),catchError((error:any)=>{
                if (error.status===400){
                    return Observable.throw('Illegal login');
                }else{
                    return Observable.throw(
                        error.json().error || 'Server error'
                    );
                }
            })
        )
    }
    getToken():String{
        var currentUser=JSON.parse(
            localStorage.getItem('currentUser'));
        var token = currentUser && currentUser.token;
        return token ? token:"";    
    }

    logout():void {
        localStorage.removeItem('currentUser');

    }

    isLoggedIn():boolean{
        if(this.getToken()!='') return true;
        else return false;
    }

    getCurrentUser(){
        if(localStorage.currentUser){
           return JSON.parse(localStorage.currentUser);
        }
        else{
            return undefined;
        }


    }
}
