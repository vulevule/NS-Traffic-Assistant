import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { UserDTO } from 'src/app/model/UserDTO';
import { AuthenticationService } from 'src/app/services/authentication.service';


@Component({
  selector: 'app-validation-dialog',
  templateUrl: './validation-dialog.component.html',
  styleUrls: ['./validation-dialog.component.css']
})
export class ValidationDialogComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal,private http: HttpClient,private authService: AuthenticationService) { }
   selectedFile:File=null;
   //private headers = new HttpHeaders({ "Content-Type": "multipart/form-data" });
  private Url = "http://localhost:8080/user";
  loggedUser : UserDTO;
  username : string;
  ngOnInit() {
    
  }
  onFileSelected(event){
    this.selectedFile=<File>event.target.files[0];


  }

  onUpload(){
    if(this.selectedFile==null){console.log("Prazan");}
   
const fd=new FormData();

console.log(this.authService.getUsername());
   fd.append('file',this.selectedFile,this.authService.getUsername()+"-"+this.selectedFile.name);

    this.http.post(`${this.Url}/validateProfile`,fd,{reportProgress:true,responseType:'text'}).subscribe(res=>{
      console.log(res);
      location.reload();
    });
  }
}
