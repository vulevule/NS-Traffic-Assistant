import { Component, OnInit, Input } from '@angular/core';
import { ReportInterface } from 'src/app/model/Report';


@Component({
  selector: 'app-display-report',
  templateUrl: './display-report.component.html',
  styleUrls: ['./display-report.component.css', './general.scss']
})
export class DisplayReportComponent implements OnInit {

  @Input() report: ReportInterface;

  total: number = 0;

  constructor() { }

  ngOnInit() {
    this.total = this.report.numOfBusTicket + this.report.numOfTramTicket + this.report.numOfMetroTicket;
  }

}
