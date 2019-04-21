import {Component, NgModule} from '@angular/core';
import {Router, RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./container/home/home.component";
import {PagenotfoundComponent} from "./container/pagenotfound/pagenotfound.component";
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'flatm8-ui';

  constructor(private auth: AuthService, private router: Router ) { }

  register() {
    this.router.navigateByUrl('register');
  }

  login() {
    this.router.navigateByUrl('login');
  }

  accountDetails() {
    this.router.navigateByUrl('userdetails');
  }

  logout() {
    this.router.navigateByUrl('logout');
  }

  home() {
    this.router.navigateByUrl('');
  }

  myflats() {
    this.router.navigateByUrl('myflats');
  }

  myentries() {
    this.router.navigateByUrl('myentries');
  }

  notifications() {
    this.router.navigateByUrl('notifications');
  }
}
