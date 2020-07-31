import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {LoginForm} from '../models/login-form.model';
import {Observable, Subscription} from 'rxjs';
import {API} from '../constants/api.const';
import {GroupCreationForm} from '../models/group-creation-form.model';
import {InvitationCreationForm} from '../models/invitation-creation-form.model';
import {SpendingDto} from '../models/spending-dto.model';
import {LoginService} from '../login/login.service';
import {UrlSegment} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  public formData = new FormData();

  constructor(private http: HttpClient, private loginService: LoginService) { }

  new() {
    this.formData = new FormData();
  }

  createGroup(groupForm: GroupCreationForm): Observable <any> {
    return this.http.post(API.httpProtocol + API.serverIp + API.createGroupAPI, groupForm, {observe: 'response'});
  }

  getGroupInfoById(groupId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.groupInfoAPI + groupId, {observe: 'response'});
  }

  getListOfGroupsByUserId(userId: string): Observable<any> {
    console.log(this.loginService.currentUser().token.token);
    return this.http.get(API.httpProtocol + API.serverIp + API.getGroupsAPI + userId + API.createGroupAPI, { observe: 'response'
    });
  }

  inviteUser(groupId: string, login: string) {
    return this.http.post(API.httpProtocol + API.serverIp + API.groupInfoAPI + groupId + API.inviteAPI, login, {observe: 'response'} );
  }

  getSpendings(groupId: string, userId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + API.groupInfoAPI + groupId + API.getGroupsAPI + userId + '/spendings',
      {observe: 'response'});
  }
  getGroupSpending(groupId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/groups/' + groupId + ' /spendings' , {observe: 'response'});
  }

  getPersonalCategoriesGraphInfo(groupId: string, userId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/graph/categories/' + groupId + '/users/' + userId, {observe: 'response'});
  }

  addSpending(spendingDto: SpendingDto) {
    return this.http.post(API.httpProtocol + API.serverIp + '/spendings', spendingDto, {observe: 'response'});
  }

  getCategoriesGraphInfo(groupId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/graph/categories/' + groupId, {observe: 'response'});
  }

  editGroup(groupId: string, groupForm: GroupCreationForm): Observable<any> {
    console.log(groupId);
    return this.http.patch(API.httpProtocol + API.serverIp + groupId, groupForm, {observe: 'response'});
  }

  getUsersGraphInfo(groupId: string): Observable<any>  {
    return this.http.get(API.httpProtocol + API.serverIp + '/graph/users/' + groupId, {observe: 'response'});
  }

  getPhoto(groupId: string): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/groups/' + groupId + '/photo', {responseType: 'text'});
  }

  addPhoto(groupId: string): Observable<any> {
    return this.http.post(API.httpProtocol + API.serverIp + '/groups/' + groupId + '/photo', this.formData, {observe: 'response'});
  }

  getUsers(): Observable<any> {
    return this.http.get(API.httpProtocol + API.serverIp + '/users', {observe: 'response'});
  }

  deleteMember(groupId: string, userId: string): Observable<any> {
    return this.http.delete(API.httpProtocol + API.serverIp + '/groups/' + groupId + '/users/' + userId, {observe: 'response'});
  }

  deleteSpending(spendingId: string): Observable<any> {
    return this.http.delete(API.httpProtocol + API.serverIp + '/spendings/' + spendingId, {observe: 'response'});
  }

  getUserLineGraphInfo(groupId: string, userId: string) {
    return this.http.get(API.httpProtocol + API.serverIp + '/graph/line/categories/' + groupId + '/users/' + userId, {observe: 'response'});
  }

  isUserHaveAccessToGroup(userId: string, urlSegment: string): Observable<any> {
    // tslint:disable-next-line:max-line-length
    return this.http.get(API.httpProtocol + API.serverIp + '/user/' + userId + '/access/group/' + urlSegment, {observe: 'response'});
  }
}
