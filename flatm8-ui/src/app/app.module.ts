import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PagenotfoundComponent } from './container/pagenotfound/pagenotfound.component';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./container/home/home.component";
import { LoginComponent } from './container/login/login.component';
import { RegisterComponent } from './container/register/register.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AppService} from "./service/AppService";

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
  { path: '**', component: PagenotfoundComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    PagenotfoundComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
    )
  ],
  providers: [AppService],
  bootstrap: [AppComponent]
})
export class AppModule { }
