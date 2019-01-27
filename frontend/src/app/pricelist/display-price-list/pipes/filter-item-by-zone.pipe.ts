import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterItemByZone',
  pure : false
})
export class FilterItemByZonePipe implements PipeTransform {

  transform(items: any[], type?: {first:boolean, second:boolean}): any[] {
    if(items) {
      if(!type.first && !type.second ) {
        return items;
      }
      return items.filter((i: any) => this.applyFilter(i, type));
    }
  }

  applyFilter(item: any, type: {first:boolean, second:boolean}):boolean {
    if(type.first && item.zone === 'FIRST') {
      return true;
    }

    if(type.second && item.zone === 'SECOND') {
      return true;
    }

  }

}
