import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {LoginService} from './login/login.service';
import {GroupService} from './group/group.service';

@Injectable({
  providedIn: 'root'
})
export class GroupGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router, private groupService: GroupService) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }
}
