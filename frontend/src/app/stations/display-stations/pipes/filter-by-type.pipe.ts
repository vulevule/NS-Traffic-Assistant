import { Pipe, PipeTransform } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';

@Pipe({
  name: 'filterByType',
  pure: false
})
export class FilterByTypePipe implements PipeTransform {

  transform(stations: StationDTO[], type?: {bus:boolean, tram:boolean, metro:boolean}): StationDTO[] {
    if(stations) {
      return stations.filter((station: StationDTO) => this.applyFilter(station, type));
    }
  }

  applyFilter(station: StationDTO, type: {bus:boolean, tram:boolean, metro:boolean}):boolean {
    if(type.bus && station.type === 'BUS') {
      return true;
    }

    if(type.tram && station.type === 'TRAM') {
      return true;
    }

    if(type.metro && station.type === 'METRO') {
      return true;
    }
  }

}
