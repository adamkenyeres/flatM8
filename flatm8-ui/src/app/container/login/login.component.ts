import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { AppService } from "../../service/AppService";
import { AuthService } from "../../auth/auth.service";
import { Observable } from 'rxjs';
import { MethodCall } from '@angular/compiler';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  credentials = {
    loginName: '',
    password: ''
  };

  loginError = false;
  unknownError = false;

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {
  }

  login() {
    this.loginError = false;
    this.unknownError = false;

    if (this.credentials.loginName.includes("@")) {
      this.loginByEmail();
    } else {
      this.loginByUserName();
    }

  }

  loginByUserName() {
    this.app.authenticateByUserName(this.credentials).subscribe(response => {
      if (response["token"]) {
        this.processLoginResponse(response);
      }
    },
      err => {
        this.handleLoginError(err);
      });
  }

  loginByEmail() {
    this.app.authenticateByEmail(this.credentials).subscribe(response => {
      this.processLoginResponse(response);
    },
      err => {
        this.handleLoginError(err);
      });
  }

  processLoginResponse(response) {
    if (response["token"]) {
      sessionStorage.setItem("token", response["token"]);
      this.router.navigateByUrl("");
    }
  }

  handleLoginError(err) {
    if (err.status == 404 || err.status == 403) {
      this.loginError = true;
    } else {
      this.unknownError = true;
    }
  }

  ngOnInit() {
    if (this.auth.isAuthenticated()) {
      alert("Already logged in!");
      this.router.navigateByUrl('');
    } else {
      sessionStorage.removeItem('token');
    }
  }

  register() {
    this.router.navigateByUrl('/register');
  }
}
