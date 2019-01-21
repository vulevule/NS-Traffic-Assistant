import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByZone',
  pure: false
})
export class FilterByZonePipe implements PipeTransform {

  transform(lines: any[], type?: {first:boolean, second:boolean}): any[] {
    if(lines) {
      if(!type.first && !type.second) {
        return lines;
      }
      return lines.filter((line: any) => this.applyFilter(line, type));
    }
  }

  applyFilter(line: any, type: {first:boolean, second:boolean}):boolean {
    if(type.first && line.type === 'FIRST') {
      return true;
    }

    if(type.second&& line.type === 'SECOND') {
      return true;
    }
  }

}
