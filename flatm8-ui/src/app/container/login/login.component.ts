import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  credentials = {
    userName: '',
    password: ''
  };

  loginError = false;
  unknownError = false;

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {
  }

  login() {
    this.loginError = false;
    this.unknownError = false;

    console.log(this.credentials.userName);
    console.log(this.credentials.userName.contains("@"));

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
