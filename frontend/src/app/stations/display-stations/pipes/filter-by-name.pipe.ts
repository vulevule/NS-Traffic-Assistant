import { Pipe, PipeTransform } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';

@Pipe({
  name: 'filterByName'
})
export class FilterByNamePipe implements PipeTransform {

  transform(stations: StationDTO[], term: String): any {
    if(stations) {
      if (term === '') {
        return stations;
      } else {
        return stations.filter(s => s.name.toLowerCase().indexOf(term.toLowerCase()) > -1);
      }
    }
  }
    

}
