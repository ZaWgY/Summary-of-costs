import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { AppComponent } from './app.component';
import {RouterModule} from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import {RegistrationService} from './registration/registration.service';
import { LoginComponent } from './login/login.component';
import { MainPageComponent } from './main-page/main-page.component';
import {LoginService} from './login/login.service';
import { SettingsComponent } from './settings/settings.component';
import {SettingsService} from './settings/settings.service';
import {MainPageService} from './main-page/main-page.service';
import { GroupCreateComponent } from './group-create/group-create.component';
import {GroupService} from './group/group.service';
import { GroupComponent } from './group/group.component';
import { GroupListComponent } from './group-list/group-list.component';
import { InvitationsComponent } from './invitations/invitations.component';
import {SearchPipe} from './group-list/search.pipe';
// tslint:disable-next-line:max-line-length
import {
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatDialogModule,
  MatInputModule,
  MatSelectModule,
  MatAutocompleteModule, MatProgressSpinnerModule, MatDividerModule
} from '@angular/material';
import { GroupEditComponent } from './group-edit/group-edit.component';
import {MatBadgeModule, MatGridListModule, MatProgressBarModule, MatTabsModule} from '@angular/material';
import {UserService} from './user.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SpendingAddComponent } from './spending-add/spending-add.component';
import {CategoryService} from './category.service';
import {TokenInterceptorService} from './token-interceptor.service';
import {AuthGuard} from './auth.guard';
import {GroupGuard} from './group.guard';
import {SearchсPipe} from './group/searchс.pipe';

const routes = [
  {path: 'registration',  component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'main', component: MainPageComponent},
  {path: 'settings', canActivate: [AuthGuard], component: SettingsComponent},
  {path: 'main', canActivate: [AuthGuard], component: MainPageComponent},
  {path: 'createGroup', canActivate: [AuthGuard], component: GroupCreateComponent},
  {path: 'groups/:id', canActivate: [GroupGuard], component: GroupComponent},
  {path: 'groups/:id/edit', canActivate: [AuthGuard], component: GroupEditComponent},
  {path: 'groups', canActivate: [AuthGuard], component: GroupListComponent},
  {path: 'invitations', canActivate: [AuthGuard], component: InvitationsComponent},
  {path: '**', canActivate: [AuthGuard], redirectTo: 'main'}
  ];

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    MainPageComponent,
    SettingsComponent,
    MainPageComponent,
    GroupCreateComponent,
    GroupComponent,
    GroupListComponent,
    SpendingAddComponent,
    GroupListComponent,
    InvitationsComponent,
    GroupEditComponent,
    SearchPipe,
    SearchсPipe
  ],
  imports: [
    BrowserModule, RouterModule.forRoot(routes), HttpClientModule, FormsModule, ReactiveFormsModule,
    BrowserAnimationsModule,
    MatBadgeModule, MatButtonModule, MatIconModule, MatCardModule, MatGridListModule,
    // tslint:disable-next-line:max-line-length
    BrowserAnimationsModule, MatButtonModule, MatIconModule, MatDialogModule, MatSelectModule, MatCardModule, MatInputModule, ChartsModule, MatProgressBarModule,
    // tslint:disable-next-line:max-line-length
    MatButtonModule, MatIconModule, MatDialogModule, MatSelectModule, MatCardModule, MatInputModule, ChartsModule, MatProgressBarModule, MatTabsModule, MatAutocompleteModule, MatProgressSpinnerModule, MatDividerModule
  ],
  providers: [RegistrationService, LoginService, SettingsService, MainPageService, GroupService, UserService, CategoryService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true // Add this line when using multiple interceptors.
  }],
  bootstrap: [AppComponent],
  entryComponents: [SpendingAddComponent]
})
export class AppModule { }
