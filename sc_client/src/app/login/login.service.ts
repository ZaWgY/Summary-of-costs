import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginForm} from '../models/login-form.model';
import {API} from '../constants/api.const';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private router: Router) {}

  authUser(loginForm: LoginForm): Observable <any> {
    return this.http.post(API.httpProtocol + API.serverIp + API.loginAPI, loginForm, {observe: 'response'});
  }

  login(token: string) {
    localStorage.setItem('currentUser', JSON.stringify({token}));
  }

  logOut() {
    return localStorage.removeItem('currentUser');
  }

  currentUser() {
    return JSON.parse(localStorage.getItem('currentUser'));
  }

}
