import { Component, OnInit } from '@angular/core';
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

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {
  }

  login() {
    this.app.authenticate(this.credentials, () => {
      this.router.navigateByUrl("");
    });
    return false;
  }

  ngOnInit() {
    if (this.auth.isAuthenticated()) {
      alert("Already logged in!");
      this.router.navigateByUrl('');
    } else {
      sessionStorage.removeItem('token');
    }
  }
}
