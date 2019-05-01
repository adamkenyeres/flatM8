import {Component, NgModule, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./container/home/home.component";
import {PagenotfoundComponent} from "./container/pagenotfound/pagenotfound.component";
import {AuthService} from "./auth/auth.service";
import {NotificationService} from "./service/NotificationService";
import {AppService} from "./service/AppService";
import {BaseRequest} from "./model/BaseRequest";
import {DomSanitizer} from "@angular/platform-browser";
import {User} from "./model/User";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'flatm8-ui';

  notifications = [];
  myRequests = [];
  navigationSubscription;
  userAvatar;
  imageType = "data:image/JPEG;base64,";
  loggedInUser;

  constructor(private auth: AuthService, private router: Router, private notificationService: NotificationService,
              private app: AppService, private activatedRoute: ActivatedRoute, private sanitizer: DomSanitizer) {

    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.fillRequestsLoadAvatar();
      }
    });
  }

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

  notificationsRedir() {
    this.router.navigateByUrl('notifications');
  }
  chat() {
    this.router.navigateByUrl('chat');
  }

  ngOnInit(): void {
  }

  fillRequestsLoadAvatar() {
    this.getAvatar();
    this.notifications = [];
    this.myRequests = [];
    this.app.getUserLoggedInUser().subscribe(data => {
      this.loggedInUser = data;
      this.notificationService.getAllRequests(this.notifications, this.myRequests, data["name"]);
    });
  }

  getAllAcceptedNotificationCount(): number {
    return this.notifications
      .filter(n => n['requestStatus'] === "PENDING")
      .length;
  }

  getAvatar() {
    this.app.getUserAvatar().subscribe(resp => {
      if (resp['content']) {
        this.userAvatar = this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']);
      }
    });
  }
}
