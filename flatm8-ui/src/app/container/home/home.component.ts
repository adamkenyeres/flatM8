import {Component, OnInit} from '@angular/core';
import {AppService} from "../../service/AppService";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  greeting = {};

  constructor(private app: AppService, private http: HttpClient, private router: Router, private auth: AuthService) {

    if (this.auth.isAuthenticated()) {
      console.log("csa");
      http.get('http://localhost:8080/user').subscribe(data => {
        this.greeting = data;
        console.log(data);
      });
    }
  }

  authenticated(): boolean {
    return this.auth.isAuthenticated();
  }

  ngOnInit() {
  }
}
