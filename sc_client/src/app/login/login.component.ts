import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegistrationService} from '../registration/registration.service';
import {Router} from '@angular/router';
import {LoginService} from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  error = '';

  constructor(private formBuilder: FormBuilder, private loginService: LoginService, private router: Router) {
    if (this.loginService.currentUser() !== null) {
      this.router.navigate(['/main']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.loginService.authUser(this.loginForm.value).subscribe(data => {
      if (data.status === 200) {
        this.loginService.login(data.body);
        this.router.navigate(['/main']);
      }
    });
    this.error = 'wrong login or password';
  }

}
