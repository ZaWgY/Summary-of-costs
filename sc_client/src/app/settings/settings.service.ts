import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserForm} from '../models/user-form.model';
import {API} from '../constants/api.const';
import {LoginService} from '../login/login.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  public formData = new FormData();

  constructor(private http: HttpClient, private loginService: LoginService) { }

  editUser(form: UserForm): Observable <any> {
    return this.http.patch(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id + API.editAPI, form, {observe: 'response'});
  }

  addPhoto(): Observable <any> {
    console.log(this.formData);
    return this.http.post(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id + '/photo', this.formData, {observe: 'response'});
  }

  new() {
    this.formData = new FormData();
  }
}
