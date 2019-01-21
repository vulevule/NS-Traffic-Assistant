import { Pipe, PipeTransform } from '@angular/core';
import { LineDTO } from 'src/app/model/LineDTO';
import { StationDTO } from 'src/app/model/StationDTO';
import { StationLineDTO } from 'src/app/model/StationLineDTO';

@Pipe({
  name: 'filterByLine',
  
})
export class FilterByLinePipe implements PipeTransform {

  transform(stations: StationDTO[], line?: LineDTO, lineName?: String): StationDTO[] {
    if(stations) {
      if(line && line.name) {
        return stations.filter(s => s.lines.filter(sl => sl.lineId === line.id).length > 0);
      } else if (lineName !== "" && lineName.length >= 2) {

        return stations.filter(s => this.applyFilter(s, lineName));
      } else {
        return stations;
      }
    }
  }

  applyFilter(station: StationDTO, lineName: String) {
    if(station.lines && station.lines.length > 0) {
      return station.lines.filter(sl => sl.lineName.toLowerCase().indexOf(lineName.toLowerCase()) > -1);
    }
  }

}
