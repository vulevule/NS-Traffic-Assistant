import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserDTO } from 'src/app/model/UserDTO';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css', '../general.scss']
})
export class TimetableComponent implements OnInit {

  logUser: UserDTO;
  role: String;

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.logUser = this.authService.getCurrentUser();
    if (this.logUser !== undefined) {
      this.role = this.logUser.role;
    }
  }

}
