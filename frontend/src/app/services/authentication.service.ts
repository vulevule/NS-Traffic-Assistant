import { Injectable } from '@angular/core';
import { Observable , of as observableOf} from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { JwtUtilsService } from 'src/app/security/jwt-utils.service';
import { LoginUserDtoInterface } from '../model/LoginUserDto';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private readonly loginPath = 'api/user/login'
    private headers = new HttpHeaders({ "Content-Type": "application/json" });


    constructor(private http: HttpClient, private jwtUtilsService: JwtUtilsService) { }


    //izmeniti da bude jednostavnije, samo sa subscribe, observable da vraca svaki servis

    login(username: string, password: string):Observable<LoginUserDtoInterface>{
        var token = '';
        var role = '';
        return this.http.post<LoginUserDtoInterface>(this.loginPath, JSON.stringify({ username, password }), { headers: this.headers, responseType: 'json' });
            // .subscribe(
            //     (data: LoginUserDtoInterface) => {
            //         alert(data.token);
            //         token = data.token;
            //         role = data.role;
            //         localStorage.setItem('currentUser', JSON.stringify({ username: username, role:role, token: token }));
            //         return observableOf(true)
            //     },
            //     err => { return observableOf(false); }
            // )
       

    }



    getToken(): String {
        var currentUser = JSON.parse(
            localStorage.getItem('currentUser'));
        var token = currentUser && currentUser.token;
        return token ? token : "";
    }
getUsername():string{
    var currentUser = JSON.parse(
        localStorage.getItem('currentUser'));
        var username=currentUser && currentUser.username;
        return username ? username : "";
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
