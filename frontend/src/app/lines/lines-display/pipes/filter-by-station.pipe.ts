import { Pipe, PipeTransform } from '@angular/core';
import { LineDTO } from 'src/app/model/LineDTO';
import { StationDTO } from 'src/app/model/StationDTO';

@Pipe({
  name: 'filterByStation'
})
export class FilterByStationPipe implements PipeTransform {

  transform(lines: LineDTO[], station?: StationDTO, stName?: String): LineDTO[] {
    if(lines) {
      if(station && station.name) {
        return lines.filter(line => line.stations.filter(sl => sl.stationId === station.id).length > 0);
      } else if (stName !== "" && stName.length >= 2) {

        return lines.filter(line => this.applyFilter(line, stName));
      } else {
        return lines;
      }
    }
  }

  applyFilter(line: LineDTO, stName: String) {
    if(line.stations && line.stations.length > 0) {
      return line.stations.filter(sl => sl.lineName.toLowerCase().indexOf(stName.toLowerCase()) > -1);
    }
  }

}
