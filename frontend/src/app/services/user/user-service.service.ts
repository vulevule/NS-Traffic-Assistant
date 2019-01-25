import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { User } from 'src/app/model/User';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private Url = "/user";

  constructor(private http: HttpClient) { }

  
  addUser(user: User): Promise<User> {
    return this.http
      .post<User>(`${this.Url}/create`, JSON.stringify(user), {
        headers: this.headers
      })
      .toPromise()
      .then(response => response as User)
      .catch(this.handleError);
  }

  handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
