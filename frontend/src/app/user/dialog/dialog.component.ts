import { Component, OnInit } from '@angular/core';
import { NgbActiveModal,NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ValidationDialogComponent } from '../validation-dialog/validation-dialog.component';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
 

  constructor(public activeModal: NgbActiveModal,private modalService: NgbModal) { }

  ngOnInit() {
    
   
  }

   showValidation(){
     
    const modalRef = this.modalService.open(ValidationDialogComponent);
    this.activeModal.close();
   }
  
   later(){

    this.activeModal.close();
    location.reload();

   }

}
