import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterItemByTrafficType',
  pure : false
})
export class FilterItemByTrafficTypePipe implements PipeTransform {

  transform(items: any[], type?: {bus:boolean, metro:boolean, tram:boolean}): any[] {
    if(items) {
      if(!type.bus && !type.metro && !type.tram) {
        return items;
      }
      return items.filter((i: any) => this.applyFilter(i, type));
    }
  }

  applyFilter(item: any, type: {bus:boolean, metro:boolean, tram:boolean}):boolean {
    if(type.bus && item.trafficType === 'BUS') {
      return true;
    }

    if(type.metro&& item.trafficType === 'METRO') {
      return true;
    }

    if(type.tram && item.trafficType === 'TRAM'){
      return true;
    }

    
  }

}
