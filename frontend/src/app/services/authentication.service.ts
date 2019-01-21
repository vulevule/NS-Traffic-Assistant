import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { JwtUtilsService } from 'src/app/security/jwt-utils.service';
import { map, catchError, tap } from 'rxjs/operators';
import { promise } from 'protractor';
import { stringify } from '@angular/compiler/src/util';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private readonly loginPath = 'api/user/login'
    private headers = new HttpHeaders({ "Content-Type": "application/json" });


    constructor(private http: HttpClient, private jwtUtilsService: JwtUtilsService) { }

     
    //izmeniti da bude jednostavnije, samo sa subscribe, observable da vraca svaki servis

    login(username: string, password: string): Observable<boolean> {
               return this.http.post(this.loginPath, JSON.stringify({ username, password }), {headers : this.headers, responseType:"text"})
            .pipe(map((res: any) => {
                console.log(res);
                alert(res);
                let token = res && res['token'];
                if (token) {
                    localStorage.setItem('currentUser', JSON.stringify({ username: username, roles: this.jwtUtilsService.getRoles(token), token: token }));
                    return true;
                }
                else {
                    return false;
                }
            }), catchError((error: any) => {
                if (error.status === 400) {
                    return Observable.throw('Illegal login');
                } else {
                    return Observable.throw(
                        error.json().error || 'Server error'
                    );
                }
            })
            )


    }



    getToken(): String {
        var currentUser = JSON.parse(
            localStorage.getItem('currentUser'));
        var token = currentUser && currentUser.token;
        return token ? token : "";
    }



    logout(): void {
        localStorage.removeItem('currentUser');

    }

    isLoggedIn(): boolean {
        if (this.getToken() != '') return true;
        else return false;
    }

    getCurrentUser() {
        if (localStorage.currentUser) {
            return JSON.parse(localStorage.currentUser);
        }
        else {
            return undefined;
        }


    }
}
