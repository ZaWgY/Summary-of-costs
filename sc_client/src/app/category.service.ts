import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {observable, Observable} from 'rxjs';
import {API} from './constants/api.const';
import {LoginService} from './login/login.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient, private loginService: LoginService) { }

  public getDefaultCategories(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/category/default', {observe: 'response'});
  }

  public getGroupAndDefaultCategoriesCategories(groupId: string): Observable<any> {
    // tslint:disable-next-line:max-line-length
    return this.http.get(API.httpProtocol + API.serverIp + '/category/group/' + groupId, { observe: 'response'});
  }

  public createCategory(groupId: string, categoryName: string): Observable<any> {
    return this.http.post(API.httpProtocol + API.serverIp + '/category/' + groupId, categoryName);
  }
}
