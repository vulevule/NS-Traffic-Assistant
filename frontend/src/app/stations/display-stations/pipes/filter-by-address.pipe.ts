import { Pipe, PipeTransform } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';

@Pipe({
  name: 'filterByAddress'
})
export class FilterByAddressPipe implements PipeTransform {

  transform(stations: StationDTO[], term: String): any {
    if(stations) {
      if (term === '') {
        return stations;
      } else {
        return stations.filter(s => s.addressName.toLowerCase().indexOf(term.toLowerCase()) > -1);
      }
    }
  }

}
