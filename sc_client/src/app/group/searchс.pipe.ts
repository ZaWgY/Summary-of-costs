import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'searchc'
})
export class SearchсPipe implements PipeTransform {
  transform(spendings: any[], name: string): any[] {
    if (!name) { return spendings; }
    return spendings.filter(user =>
      user.categoryName.toLowerCase().includes(name.toLowerCase()) || user.date.toLowerCase().includes(name.toLowerCase()) || user.login.toLowerCase().includes(name.toLowerCase())
    );
  }
}
