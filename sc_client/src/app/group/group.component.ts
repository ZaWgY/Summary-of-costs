import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import {GroupService} from './group.service';
import {Router} from '@angular/router';
import {InvitationCreationForm} from '../models/invitation-creation-form.model';
import {UserService} from '../user.service';
import {LoginService} from '../login/login.service';
import {SpendingDto} from '../models/spending-dto.model';
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {GroupCreateComponent} from '../group-create/group-create.component';
import {SpendingAddComponent} from '../spending-add/spending-add.component';
import {ChartDataSets, ChartOptions, ChartType} from 'chart.js';
import {Color, Label} from 'ng2-charts';
import {Observable, Subscriber} from 'rxjs';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {debounceTime, finalize, map, startWith, switchMap, tap} from 'rxjs/operators';
import {tryCatch} from 'rxjs/internal-compatibility';

export class User {
  id: string;
  login: string;
  firstName: string;
  secondName: string;
  imgName: string;
}

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  public pieChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      position: 'top',
    },
    plugins: {
      datalabels: {
        formatter: (value, ctx) => {
          const label = ctx.chart.data.labels[ctx.dataIndex];
          return label;
        },
      },
    }
  };
  public pieChartLabels: Label[] = []; // ['Download', 'Sales'], ['In', 'Store', 'Sales'], 'Mail Sales'
  public pieChartData: number[]  = []; // 300, 500, 100
  public pieChartUsersLabels: Label[] = []; // ['Download', 'Sales'], ['In', 'Store', 'Sales'], 'Mail Sales'
  public pieChartUsersData: number[]  = [];
  public pieChartPersLabels: Label[] = []; // ['Download', 'Sales'], ['In', 'Store', 'Sales'], 'Mail Sales'
  public pieChartPersData: number[]  = [];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  // public pieChartPlugins = [pluginDataLabels];
  public pieChartColors = [
    {
      backgroundColor: ['rgba(255,0,0,0.3)', 'rgba(0,255,0,0.3)',
        'rgba(0,0,255,0.3)', 'rgba(100, 100, 50,0.3)',
        'rgba(255,0,255,0.3)', 'rgba(150,0,200,0.3)', 'rgba(255,255,0,0.3)'],
    },
  ];

  public lineChartData: ChartDataSets[] = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' },
    // { data: [180, 480, 770, 90, 1000, 270, 400], label: 'Series C', yAxisID: 'y-axis-1' }
  ];
  public lineChartLabels: Label[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{}],
      yAxes: [
        {
          id: 'y-axis-0',
          position: 'left',
        }
      ]
    },
    annotation: {
      annotations: [
        {
          type: 'line',
          mode: 'vertical',
          scaleID: 'x-axis-0',
          value: 'March',
          borderColor: 'orange',
          borderWidth: 2,
          label: {
            enabled: true,
            fontColor: 'orange',
            content: 'LineAnno'
          }
        },
      ],
    },
  };
  public lineChartColors: Color[] = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // red
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';

  // @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;

  groupId: string;
  name: string;
  description: string;
  curAmount: string;
  maxAmount: string;
  listOfUsers: Array<User>;
  spendings: Array<any>;
  categoryGraphData: Array<any>;
  usersGraphData: Array<any>;
  groupSpendings: Array<any>;
  categoryGraphPersonalData: Array<any>;
  role: string;
  invitedUser: string;
  spendingAdd: string;
  progress: number;
  nginxUrl = 'http://ec2-18-220-165-203.us-east-2.compute.amazonaws.com/images/';
  photoLink: string;
  myControl = new FormControl();
  options: User[] = [];
  filteredOptions: Observable<User[]>;
  searchName = '';
  invitationInfo = '';
  invitationSubmitted = false;

  constructor(private location: Location, private groupService: GroupService,
              private router: Router, private userService: UserService,
              private loginService: LoginService,
              private dialog: MatDialog, private fb: FormBuilder) {
    const pathString = location.path();
    this.groupId  = pathString.split('/')[2];
    console.log(this.groupId);

  }

  ngOnInit() {
    this.refresh();
    this.userService.getUsersRole(this.groupId, this.loginService.currentUser().token.id).subscribe(data => {
      this.role = data.body;
    });
    this.progress = (Number(this.curAmount) / Number(this.maxAmount)) * 100;
    console.log('progress = ' + this.progress);
    this.filteredOptions = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.login),
        map(login => login ? this._filter(login) : this.options.slice())
      );
  }

  displayFn(user?: User): string | undefined {
    return user ? user.login : undefined;
  }

  private _filter(name: string): User[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.login.toLowerCase().indexOf(filterValue) === 0 || option.firstName.toLowerCase().indexOf(filterValue) === 0 || option.secondName.toLowerCase().indexOf(filterValue) === 0);
  }

  inviteUser() {
     this.groupService.inviteUser(this.groupId, this.myControl.value.login).subscribe(data => {
          console.log(data);
          if (data.status === 201) {
            this.invitationInfo = 'Приглашение отправлено';
            return;
          }
       }
     );
     this.invitationInfo = 'Пользователь уже получил приглашение';
     console.log(this.invitationInfo);
     this.myControl.value.login = '';
  }

  addSpending() {
    this.groupService.addSpending(new SpendingDto(this.spendingAdd, this.groupId, '1', this.loginService.currentUser().token.id))
      .subscribe(data => {
          console.log(data.status);
      });
  }

  popUpCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '30%';
    this.dialog.open(SpendingAddComponent, dialogConfig);
    this.dialog.afterAllClosed.subscribe(() => {
      this.refresh();
    });
    /*this.dialog._afterAllClosed._subscribe();
    });*/
  }

  deleteMember(groupId: string, user: User) {
    this.groupService.deleteMember(groupId, user.id).subscribe();
    this.listOfUsers.splice(this.listOfUsers.indexOf(user), 1);
  }

  deleteSpending(spendingId: string) {
    this.groupService.deleteSpending(spendingId).subscribe(() => this.refresh());
  }

  refresh() {
    this.groupService.getGroupInfoById(this.groupId).subscribe(data => {
      if(data.status === 0) {
        this.loginService.logOut();
        this.router.navigate(['/login']);
        return;
      }
      console.log('STATUS ' + data.status);
      if(data.status === 500) {
        this.router.navigate(['/groups']);
        return;
      }
      this.name = data.body.name;
      this.description = data.body.description;
      this.listOfUsers = data.body.listOfUsers;
      this.curAmount = data.body.currentAmount;
      this.maxAmount = data.body.maxAmount;
      this.progress = data.body.progress;
      this.userService.getUserById(this.loginService.currentUser().token.id).subscribe(dt => {
        let flag = 0;
        for (let i = 0; i < this.listOfUsers.length ; i++) {
          console.log(this.listOfUsers[i].login + ' ' + dt.body.login);
          if (this.listOfUsers[i].login === dt.body.login) {
            flag++;
          }
        }
        if (flag === 0) {
          this.router.navigate(['/groups']);
        }
      });
      /*his.userService.getUserById(this.loginService.currentUser().token.id).subscribe(dt => {
        let flag = 0;
        for (let i = 0; i < this.listOfUsers.length ; i++) {
          if (this.listOfUsers[i].login === dt.body.login) {
            flag++;
          }
          if (flag === 0) {
            this.router.navigate(['/groups']);
          }
        }
      });*/
    });
    this.groupService.getSpendings(this.groupId, this.loginService.currentUser().token.id).subscribe(data => {
      this.spendings = data.body;
    });
    this.groupService.getGroupSpending(this.groupId).subscribe(data => {
      this.groupSpendings = data.body;
    });
    this.groupService.getCategoriesGraphInfo(this.groupId).subscribe(data => {
      this.categoryGraphData = data.body;
      for (let i = 0; i < this.categoryGraphData.length ; i++) {
          this.pieChartData[i] = this.categoryGraphData[i].amount;
          this.pieChartLabels[i] = this.categoryGraphData[i].categoryName;
      }
    });
    this.groupService.getUsersGraphInfo(this.groupId).subscribe(data => {
        this.usersGraphData = data.body;
        for (let i = 0; i < this.usersGraphData.length ; i++) {
        this.pieChartUsersData[i] = this.usersGraphData[i].amount;
        this.pieChartUsersLabels[i] = this.usersGraphData[i].login;
      }
    });
    this.groupService.getPersonalCategoriesGraphInfo(this.groupId, this.loginService.currentUser().token.id).subscribe(data => {
      this.categoryGraphPersonalData = data.body;
      for (let i = 0; i < this.categoryGraphPersonalData.length ; i++) {
        this.pieChartPersData[i] = this.categoryGraphPersonalData[i].amount;
        this.pieChartPersLabels[i] = this.categoryGraphPersonalData[i].categoryName;
      }
    });
    this.groupService.getUserLineGraphInfo(this.groupId, this.loginService.currentUser().token.id).subscribe(data => {

    });
    this.groupService.getPhoto(this.groupId).subscribe(data => {
      this.photoLink = this.nginxUrl + data;
    });
    this.groupService.getUsers().subscribe(data => {
      this.options = data.body;
      this.options = this.options.filter(x => !this.listOfUsers.some(y => x.login === y.login));
      console.log(this.options);
    });
  }

}
