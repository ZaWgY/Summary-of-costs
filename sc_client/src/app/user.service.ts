import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {API} from './constants/api.const';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsersRole(groupId: string, userId: string): Observable<any> {
     return this.http.get(API.httpProtocol + API.serverIp + API.groupInfoAPI + groupId + API.roleAPI + userId, {observe: 'response'});
  }

  getUserById(userId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.usersAPI + '/' + userId, {observe: 'response'});
  }
}
