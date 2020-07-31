import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RegistrationForm} from '../models/registration-form.model';
import {Observable} from 'rxjs';
import {API} from '../constants/api.const';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  registerUser(registrationForm: RegistrationForm): Observable <any> {
    console.log(registrationForm);
    return this.http.post(API.httpProtocol + API.serverIp + API.registrationAPI, registrationForm);
  }
}
