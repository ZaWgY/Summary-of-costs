import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {API} from '../constants/api.const';
import {GroupInvitation} from '../models/group-invitation.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../login/login.service';

@Injectable({
  providedIn: 'root'
})
export class InvitationsService {

  constructor(private http: HttpClient, private loginService: LoginService) { }

  getInvitations(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.usersAPI + '/' + this.loginService.currentUser().token.id + API.invitationsAPI);
  }
  acceptInvite(groupInvitation: GroupInvitation){
    return this.http.post(API.httpProtocol + API.serverIp + API.invitationsAPI + '/accept', groupInvitation);
  }
  declineInvite(groupInvitation: GroupInvitation) {
    return this.http.patch(API.httpProtocol + API.serverIp + API.invitationsAPI + '/decline', groupInvitation);
  }
}
