import { Component, OnInit } from '@angular/core';
import {GroupService} from '../group/group.service';
import {LoginService} from '../login/login.service';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  groups: Array<any>;
  nginxUrl = 'http://ec2-18-220-165-203.us-east-2.compute.amazonaws.com/images/';
  photoLink: string;
  searchName = '';

  constructor(private groupService: GroupService, private loginService: LoginService, private router: Router) { }

  ngOnInit() {
    this.groupService.getListOfGroupsByUserId(this.loginService.currentUser().token.id).subscribe(data => {
      console.log(data.body);
      this.groups = data.body;
    });
  }

  goToGroupPage(groupId: string) {
    this.router.navigate(['/groups/' + groupId]);
  }

  goToCreateGroupPage() {
    this.router.navigate(['/createGroup']);
  }
}
