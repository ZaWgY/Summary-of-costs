import { Component, OnInit } from '@angular/core';
import {RegistrationService} from './registration.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  signUpForm: FormGroup;
  submitted = false;
  error = '';
  constructor(private formBuilder: FormBuilder, private registrationService: RegistrationService, private router: Router, private loginService: LoginService) {
    if (this.loginService.currentUser() !== null) {
      this.router.navigate(['/main']);
    }
  }

  ngOnInit() {
    this.signUpForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      secondName: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.pattern('(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])')]]
    });
  }

  get f() { return this.signUpForm.controls; }

  onSubmit() {
    this.submitted = true;
    console.log(this.signUpForm.controls.email.value);
    if (this.signUpForm.invalid) {
      return;
    }
    this.registrationService.registerUser(this.signUpForm.value).subscribe(data => {
      this.router.navigate(['/login']);
    });
  }
}
