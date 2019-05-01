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

  credentials = {email: '', password: ''};

  loginError = false;
  unknownError = false;

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {
  }

  login() {
    this.loginError = false;
    this.unknownError = false;
    this.app.authenticate(this.credentials).subscribe(response => {
        if (response["token"]) {
          sessionStorage.setItem("token", response["token"]);
          this.router.navigateByUrl("");
        }
      },
      err => {
        if (err.status == 404 || err.status == 403) {
          this.loginError = true;
        } else {
          this.unknownError = true;
        }
      });
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
