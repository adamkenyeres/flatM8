import {Component, Injectable, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {AppService} from "../../service/AppService";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
@Injectable()
export class RegisterComponent implements OnInit {
  objectKeys = Object.keys;

  private CONST_TENANT_STAY_TYPES = {
    "NONE": "I would rather not answer.",
    "AFTERNOON": "Mostly at home in the afternoons.",
    "MORNING": "Mostly at home in the mornings.",
    "NIGHT": "Mostly at home only through nights.",
    "ALL_DAY": "Usually at home all day.",
    "OTHER": "Other, will describe in the comments."
  };

  private CONST_TENANT_TYPES = {
    "NONE": "I would rather not answer.",
    "BELOW_UNI": "Going to High school.",
    "UNIVERSITY": "Going to University/College.",
    "WORK": "Working.",
    "STAY_AT_HOME": "I am staying at home.",
    "OTHER": "Other, will describe in the comments."
  };

  credentials: User = new User();
  error = false;

  typesArray = [];
  stayTypesArray = [];

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {

    for (const key in this.CONST_TENANT_TYPES) {
      this.typesArray.push(this.CONST_TENANT_TYPES[key])
    }

    for (const key in this.CONST_TENANT_STAY_TYPES) {
      this.stayTypesArray.push(this.CONST_TENANT_STAY_TYPES[key])
    }
  }

  register() {
    this.credentials.roles = [{
      "name": "USER"
    }];

    console.log(this.credentials);
    this.app.register(this.credentials, () => {
      this.router.navigateByUrl('/login');
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
