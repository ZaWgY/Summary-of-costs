import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {LoginService} from './login/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {
  }

  // tslint:disable-next-line:max-line-length
  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.loginService.currentUser() === null) {
      this.router.navigate(['/login']);
      return false;
    } else {
      return true;
    }
  }

}
