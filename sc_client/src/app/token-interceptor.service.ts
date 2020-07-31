import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {LoginService} from './login/login.service';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(private loginService: LoginService, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.loginService.currentUser() === null) {
      return next.handle(req);
    }
    const tokenizedReq = req.clone({
      setHeaders: {Authorization: this.loginService.currentUser().token.token}
    });
    return next.handle(tokenizedReq).pipe(catchError(err => {
        if (err instanceof HttpErrorResponse && err.status === 0) {
            this.router.navigate(['/login']);
            this.loginService.logOut();
        } else {
          return throwError(err);
        }
    }));
  }
}
