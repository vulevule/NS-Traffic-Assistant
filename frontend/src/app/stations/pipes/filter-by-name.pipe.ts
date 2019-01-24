import { Pipe, PipeTransform } from '@angular/core';
import { StationDTO } from 'src/app/model/StationDTO';

@Pipe({
  name: 'filterByName',
  pure: false
})
export class FilterByNamePipe implements PipeTransform {

  transform(stations: any[], term: String): any {
    if(stations) {
      if (term === '' || term.length < 2) {
        return stations;
      } else {
        return stations.filter(s => s.name.toLowerCase().indexOf(term.toLowerCase()) > -1);
      }
    }
  }
    

}
