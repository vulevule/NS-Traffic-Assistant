import { Component, OnInit, Input } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';
import { LineDTO } from 'src/app/model/LineDTO';

@Component({
  selector: 'app-lines-sidebar',
  templateUrl: './lines-sidebar.component.html',
  styleUrls: ['./lines-sidebar.component.css']
})
export class LinesSidebarComponent implements OnInit {

  @Input()
  stations:StationDTO[];

  @Input()
  lines:LineDTO[];

  constructor() { }

  ngOnInit() {
  }

}
