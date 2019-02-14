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
    UserdetailsComponent
  ],
  imports: [
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
  providers: [FlatService, AppService,
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
