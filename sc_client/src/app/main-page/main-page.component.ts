import { Component, OnInit } from '@angular/core';
import {LoginService} from '../login/login.service';
import {Router} from '@angular/router';
import {UserForm} from '../models/user-form.model';
import {MainPageService} from './main-page.service';
import {GroupInvitation} from '../models/group-invitation.model';
import {MatIconRegistry} from '@angular/material';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  currentUser: any;
  ownGroups: Array<any>;
  nginxUrl = 'http://ec2-18-220-165-203.us-east-2.compute.amazonaws.com/images/';
  photoLink: string;
  constructor(private loginService: LoginService, private router: Router, private mainPageService: MainPageService, private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    if (this.loginService.currentUser() === null) {
      this.router.navigate(['/login']);
    }
    iconRegistry.addSvgIcon(
      'email',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/email.svg'));
  }

  ngOnInit() {
    this.mainPageService.getInfo().subscribe(data => this.currentUser = data);
    this.mainPageService.getOwnGroups().subscribe(data => this.ownGroups = data);
    this.mainPageService.getPhoto().subscribe(data => {
      console.log(data);
      this.photoLink = this.nginxUrl + data;
      console.log(this.photoLink);
    });
  }

}
