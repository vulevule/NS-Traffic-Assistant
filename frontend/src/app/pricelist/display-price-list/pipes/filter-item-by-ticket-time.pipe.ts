import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterItemByTicketTime',
  pure : false
})
export class FilterItemByTicketTimePipe implements PipeTransform {

  transform(items: any[], type?: {annual:boolean, month:boolean, daily:boolean, single:boolean}): any[] {
    if(items) {
      if(!type.annual && !type.month && !type.single && !type.daily) {
        return items;
      }
      return items.filter((i: any) => this.applyFilter(i, type));
    }
  }

  applyFilter(item: any, type: {annual:boolean, month:boolean, daily:boolean, single:boolean}):boolean {
    if(type.annual && item.timeType === 'ANNUAL') {
      return true;
    }

    if(type.month&& item.timeType === 'MONTH') {
      return true;
    }

    if(type.daily && item.timeType === 'DAILY'){
      return true;
    }

    if(type.single && item.timeType === 'SINGLE'){
      return true;
    }
  }

}
