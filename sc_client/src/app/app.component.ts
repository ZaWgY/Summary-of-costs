import { Component } from '@angular/core';
import {LoginService} from './login/login.service';
import {interval, timer} from 'rxjs';
import {InvitationsService} from './invitations/invitations.service';
import {Observable} from 'rxjs';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  invitations: Array<any>;
  length = 0;
  title = 'scclient';
  constructor(private loginService: LoginService, private invitationsService: InvitationsService) {
    if (this.loginService.currentUser() !== null) {
      this.invitationsService.getInvitations().subscribe(data => {
        this.invitations = data;
        if (this.invitations !== undefined) {
          this.length = this.invitations.length;
        } else {
          this.length = 0;
        }
      });
      interval(20000) // repeats every 5 seconds
        .pipe(switchMap(() => this.invitationsService.getInvitations()))
        .subscribe(data => {
          this.invitations = data;
          if (this.invitations !== undefined) {
            this.length = this.invitations.length;
            console.log(this.length);
          } else {
            this.length = 0;
          }
        });
      }
    console.log(this.invitations);
  }
  invAmount() {
    if (this.invitations === undefined) {
      return 0;
    }
    return this.invitations.length;
  }
}
