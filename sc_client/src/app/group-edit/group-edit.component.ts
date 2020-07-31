import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SettingsService} from '../settings/settings.service';
import {Router} from '@angular/router';
import {LoginService} from '../login/login.service';
import {MainPageService} from '../main-page/main-page.service';
import {Location} from '@angular/common';
import {GroupService} from '../group/group.service';
import {MatIconRegistry} from '@angular/material';
import {DomSanitizer} from '@angular/platform-browser';
import {CategoryService} from '../category.service';

@Component({
  selector: 'app-group-edit',
  templateUrl: './group-edit.component.html',
  styleUrls: ['./group-edit.component.css']
})
export class GroupEditComponent implements OnInit {

  group: any;
  editForm: FormGroup;
  submitted = false;
  error = '';
  info = '';
  categoryName: '';
  categoryInfo = '';
  categoryForm: FormGroup;

  constructor(private location: Location, private formBuilder: FormBuilder, private groupService: GroupService, private router: Router, private loginService: LoginService, private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer, private categoryService: CategoryService) {
    if (this.loginService.currentUser() === null) {
      this.router.navigate(['/login']);
    }
    iconRegistry.addSvgIcon(
      'return',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/return.svg'));
    const pathString = location.path();
    console.log(pathString.split('/')[2]);
    this.groupService.getGroupInfoById(pathString.split('/')[2]).subscribe(data => this.group = data.body);
  }

  ngOnInit() {
    this.editForm = this.formBuilder.group({
      creatorId: [this.loginService.currentUser().token.id],
      name: [''],
      description: [''],
      maxAmount: []
    });
    this.categoryForm = this.formBuilder.group({categoryName: ['']});
  }

  onSubmit() {
    const pathString = this.location.path();
    this.submitted = true;
    if (this.editForm.invalid) {
      return;
    }
    this.groupService.editGroup(pathString, this.editForm.value).subscribe(data => {
      if (data.status === 200) {
        this.error = 'Редактирование прошло успешно';
      }
    });
  }

  processFile(file: any) {
    console.log( 'file', file )
    for ( let i = 0; i < file.length; i++ ) {
      this.groupService.formData.append( 'file', file[i], file[i].name );
    }
  }

  addPhoto() {
    const pathString = this.location.path();
    this.groupService.addPhoto(pathString.split('/')[2]).subscribe(data => {
      if (data.status === 200) {
        this.info = 'Фотография добавлена';
      }
    });
    this.groupService.new();
  }

  return() {
    const pathString = this.location.path();
    this.router.navigate(['/groups/' + pathString.split('/')[2]]);
  }

  addCategory() {
    const pathString = this.location.path();
    console.log(this.categoryName);
    this.categoryService.createCategory(pathString.split('/')[2], this.categoryName).subscribe(data => {
      if (data === true) {
        this.categoryInfo = 'Категория добавлена';
        this.categoryName = '';
      } else {
        this.categoryInfo = 'Данная категория уже существует';
      }
    });
  }
}
