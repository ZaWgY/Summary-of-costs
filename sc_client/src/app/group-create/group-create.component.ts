import { Component, OnInit } from '@angular/core';
import {GroupService} from '../group/group.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GroupCreationForm} from '../models/group-creation-form.model';
import {LoginService} from '../login/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {

  submitted = false;
  groupForm: FormGroup;
  constructor(private groupService: GroupService, private formBuilder: FormBuilder, private loginService: LoginService,
              private router: Router) { }

  ngOnInit() {
    this.groupForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      maxAmount: ['', Validators.required]
    });
  }

  get f() { return this.groupForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.groupForm.invalid) {
      return;
    }
    if ( this.groupForm.value.maxAmount < 0) {
      return;
    }
    this.groupService.createGroup(new GroupCreationForm(this.groupForm.value.name,
      this.groupForm.value.description,
      this.loginService.currentUser().token.id, this.groupForm.value.maxAmount))
      .subscribe(data => {
        if (data.status === 201) {
          console.log(' kruto');
          this.router.navigate(['/groups/' + data.body.groupId]);
        } else {
          console.log(' ne kruto');
        }
      });
    /*this.groupService.authUser(this.loginForm.value).subscribe(data => {
      if (data.status === 200) {
        this.loginService.login(data.body);
        this.router.navigate(['/main']);
      }
    });*/
  }

}
