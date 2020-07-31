import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegistrationService} from '../registration/registration.service';
import {Router} from '@angular/router';
import {LoginService} from '../login/login.service';
import {SettingsService} from './settings.service';
import {MainPageService} from '../main-page/main-page.service';
import {UserForm} from '../models/user-form.model';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  currentUser: any;
  editForm: FormGroup;
  submitted = false;
  error = '';
  info = '';

  constructor(private formBuilder: FormBuilder, private settingsService: SettingsService, private router: Router, private loginService: LoginService, private mainPageService: MainPageService) {
    if (this.loginService.currentUser() === null) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit() {
    this.mainPageService.getInfo().subscribe(data => this.currentUser = data);
    // console.log(this.currentUser.firstName);
    this.editForm = this.formBuilder.group({
      firstName: ['', Validators.minLength(3)],
      secondName: ['', Validators.minLength(3)],
      login: [''],
      password: [''],
      email: ['', Validators.pattern('^[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$')]
    });
  }

  get f() { return this.editForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.editForm.invalid) {
      if (this.editForm.controls.firstName.invalid) {
        this.error = 'Имя должно содержать хотя бы 3 буквы';
      }
      if (this.editForm.controls.secondName.invalid) {
        this.error = 'Фамилия должна содержать хотя бы 3 буквы';
      }
      if (this.editForm.controls.email.invalid) {
        this.error = 'Неверный формат почты';
      }
      console.log(this.error);
      return;
    }
    this.settingsService.editUser(this.editForm.value).subscribe(data => {
      if (data.status === 200) {
        this.error = 'Профиль успешно отредактирован';
      } else {
        this.error = 'Почта занята другим пользователем';
      }
    });
  }

  processFile(file: any) {
    console.log( 'file', file )
    for ( let i = 0; i < file.length; i++ ) {
      this.settingsService.formData.append( 'file', file[i], file[i].name );
    }
  }

  addPhoto() {
    this.settingsService.addPhoto().subscribe(data => {
      if (data.status === 200) {
        this.info = 'Фотография добавлена';
      }
    });
    this.settingsService.new();
  }
}
