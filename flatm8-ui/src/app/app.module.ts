import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PagenotfoundComponent } from './container/pagenotfound/pagenotfound.component';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./container/home/home.component";
import { LoginComponent } from './container/login/login.component';
import { RegisterComponent } from './container/register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AppService} from "./service/AppService";
import {TokenInterceptor} from "./auth/token.interceptor";
import {AuthService} from "./auth/auth.service";
import {JwtHelperService, JwtModule} from "@auth0/angular-jwt";
import { LogoutComponent } from './container/logout/logout.component';
import { MyflatsComponent } from './container/myflats/myflats.component';
import {FlatService} from "./service/FlatService";
import { CreateflatComponent } from './container/createflat/createflat.component';
import { UserdetailsComponent } from './container/userdetails/userdetails.component';
import { MyentriesComponent } from './container/myentries/myentries.component';
import {EntryService} from "./service/EntryService";
import { CreateentryComponent } from './container/createentry/createentry.component';
import { NotificationsComponent } from './container/notifications/notifications.component';
import {NotificationService} from "./service/NotificationService";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatExpansionModule, MatIconModule,
  MatProgressBarModule,
  MatSliderModule,
  MatSlideToggleModule
} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {Ng5SliderModule} from "ng5-slider";
import { BrowseComponent } from './container/browse/browse.component';
import { SlashnComponent } from './container/slashn/slashn.component';
import { ChatComponent } from './container/chat/chat.component';
import {ChatService} from "./service/ChatService";
import {ListUploadComponent} from './container/list.upload/list.upload.component';
import {DetailsUploadComponent} from './container/details.upload/details.upload.component';
import {FormUploadComponent} from './container/form.upload/form.upload.component';
import {UploadFileService} from "./service/UploadFileService";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: { title: 'FlatM8 Home' }
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'myflats',
    component: MyflatsComponent
  },
  {
    path: 'userdetails',
    component: UserdetailsComponent
  },
  {
    path: 'createflat',
    component: CreateflatComponent
  },
  {
    path: 'myentries',
    component: MyentriesComponent
  },
  {
    path: 'createentry',
    component: CreateentryComponent
  },
  {
    path: 'notifications',
    component: NotificationsComponent
  },
  {
    path: 'slashn',
    component: SlashnComponent
  },
  {
    path: 'chat',
    component: ChatComponent
  },
  { path: '**', component: PagenotfoundComponent }
];

export function tokenGetter() {
  return sessionStorage.getItem('token');
}

@NgModule({
  declarations: [
    AppComponent,
    PagenotfoundComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    MyflatsComponent,
    CreateflatComponent,
    UserdetailsComponent,
    MyentriesComponent,
    CreateentryComponent,
    NotificationsComponent,
    BrowseComponent,
    SlashnComponent,
    ChatComponent,
    ListUploadComponent,
    DetailsUploadComponent,
    FormUploadComponent
  ],
  imports: [
    MatExpansionModule,
    MatButtonModule,
    MatCheckboxModule,
    MatSliderModule,
    MatSlideToggleModule,
    Ng5SliderModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: []
      }
    }),
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
    )
  ],
  providers: [EntryService, FlatService, AppService, NotificationService, ChatService, UploadFileService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthService,
    JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
