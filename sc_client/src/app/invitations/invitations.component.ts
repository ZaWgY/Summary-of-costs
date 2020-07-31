import { Component, OnInit } from '@angular/core';
import {InvitationsService} from './invitations.service';
import {GroupInvitation} from '../models/group-invitation.model';
import {MatIconRegistry} from '@angular/material';
import {DomSanitizer} from '@angular/platform-browser';
import {interval} from 'rxjs';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-invitations',
  templateUrl: './invitations.component.html',
  styleUrls: ['./invitations.component.css']
})
export class InvitationsComponent implements OnInit {
  invitations: Array<any>;

  constructor(private invitationsService: InvitationsService, iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'thumbs-up',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/checked.svg'));
    iconRegistry.addSvgIcon(
      'decline',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/decline.svg'));
  }

  ngOnInit() {
    this.invitationsService.getInvitations().subscribe(data => this.invitations = data);
    interval(20000) // repeats every 5 seconds
      .pipe(switchMap(() => this.invitationsService.getInvitations()))
      .subscribe(data => {
        this.invitations = data;
        console.log(this.invitations);
      });
  }
  invitationsAmount() {
    return this.invitations.length;
  }
  accept(groupInvitation: GroupInvitation) {
    this.invitationsService.acceptInvite(groupInvitation).subscribe();
    this.invitations.splice(this.invitations.indexOf(groupInvitation), 1);
  }
  decline(groupInvitation: GroupInvitation) {
    this.invitationsService.declineInvite(groupInvitation).subscribe();
    this.invitations.splice(this.invitations.indexOf(groupInvitation), 1);
  }
}
