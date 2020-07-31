import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {
  transform(groups: any[], name: string): any[] {
    if (!name) { return groups; }
    return groups.filter(user => {
      return user.name.toLowerCase().includes(name.toLowerCase());
    });
  }
}
