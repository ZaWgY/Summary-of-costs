import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {API} from '../constants/api.const';
import {LoginService} from '../login/login.service';
import {Observable} from 'rxjs';
import {GroupInvitation} from '../models/group-invitation.model';

@Injectable({
  providedIn: 'root'
})
export class MainPageService {

  constructor(private http: HttpClient, private loginService: LoginService) { }

  getInfo(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id);
  }
  getOwnGroups(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id + API.createGroupAPI + API.rolesAPI + '?groupRole=OWNER');
  }
  getPhoto(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id + '/photo', {responseType: 'text'});
  }
}
