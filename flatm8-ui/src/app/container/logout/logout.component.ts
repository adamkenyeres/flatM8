import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
    if (this.auth.isAuthenticated()) {
      this.logout();
      this.router.navigateByUrl("");
    } else {
      alert("Not logged in");
      this.router.navigateByUrl("");
    }
  }

  private logout() {
    sessionStorage.removeItem("token");
  }

}
