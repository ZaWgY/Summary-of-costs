import { Component, OnInit } from '@angular/core';
import {GroupComponent} from '../group/group.component';
import { Location } from '@angular/common';
import {GroupService} from '../group/group.service';
import {SpendingDto} from '../models/spending-dto.model';
import {LoginService} from '../login/login.service';
import {CategoryService} from '../category.service';
import {MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';

@Component({
  selector: 'app-spending-add',
  templateUrl: './spending-add.component.html',
  styleUrls: ['./spending-add.component.css']
})
export class SpendingAddComponent implements OnInit {

  amount: string;
  groupId: string;
  categoryId: string;
  categoryArr: Array<any>;
  info: string;

  constructor(private location: Location, private groupService: GroupService,
              private loginService: LoginService, private categoryService: CategoryService,
              private dialogRef: MatDialogRef<SpendingAddComponent>,
              // private groupComponent: GroupComponent
              private router: Router
              ) { }

  ngOnInit() {
    const pathString = this.location.path();
    this.groupId = pathString.split('/')[2];
    this.categoryService.getGroupAndDefaultCategoriesCategories(this.groupId).subscribe(data => {
      this.categoryArr = data.body;
    });
  }

  addSpending() {
    try {
      if (this.categoryId === undefined || this.amount === null || this.amount.length < 1) {
        this.info = 'Введите все данные о затрате';
        return;
      }
      const num = +this.amount;
      if (num < 0) {
        this.info = 'Некорректная сумма';
        return;
      }
    } catch (e) {
      this.info = 'Введите все данные о затрате';
      return;
    }
    this.groupService.addSpending(new SpendingDto(this.amount, this.groupId, this.categoryId, this.loginService.currentUser().token.id))
      .subscribe();
    this.router.navigate(['/groups/' + this.groupId]);
    this.dialogRef.close();

  //  this.groupComponent.refresh();
  }
}
