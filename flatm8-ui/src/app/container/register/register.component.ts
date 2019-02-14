import {Component, Injectable, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {AppService} from "../../service/AppService";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Form, FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
@Injectable()
export class RegisterComponent implements OnInit {
  objectKeys = Object.keys;

  credentials: User = new User();

  typesArray = [];
  stayTypesArray = [];
  error = false;
  userExistsError = false;

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {
    for (const key in this.app.CONST_TENANT_TYPES) {
      this.typesArray.push(this.app.CONST_TENANT_TYPES[key])
    }

    for (const key in this.app.CONST_TENANT_STAY_TYPES) {
      this.stayTypesArray.push(this.app.CONST_TENANT_STAY_TYPES[key])
    }
  }

  register() {
    this.error = false;
    this.userExistsError = false;
    this.credentials.roles = [{
      "name": "USER"
    }];

    console.log(this.credentials.email);
      this.app.register(this.credentials).subscribe(resp => {
        this.router.navigateByUrl("/login");
      },
        err => {
        if (err.status === 404) {
          this.userExistsError = true;
        } else {
          this.error = true;
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
}
