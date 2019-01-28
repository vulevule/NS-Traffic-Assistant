import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { User } from 'src/app/model/User';
import { Observable } from 'rxjs';
import { RegisterDTOInterface } from 'src/app/model/RegisterDTO';
import { EditDtoInterface } from 'src/app/model/EditProfileDto';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private headers = new HttpHeaders({ "Content-Type": "application/json" });
  private Url = "api/user";

  constructor(private http: HttpClient) { }

  
  addUser(user: RegisterDTOInterface): Observable<RegisterDTOInterface> {
    return this.http
      .post<RegisterDTOInterface>(`${this.Url}/create`, JSON.stringify(user), {
        headers: this.headers
      })
      
  }
  editProfile(user:EditDtoInterface):Observable<EditDtoInterface>{
   return this.http.put<EditDtoInterface>(`${this.Url}/profileUpdate`, JSON.stringify(user), {
    headers: this.headers
  })

  }
  getUser():Observable<EditDtoInterface>{
    return this.http.get<EditDtoInterface>(`${this.Url}/getUser`,{
      headers: this.headers
    })
  }



 
  
}
