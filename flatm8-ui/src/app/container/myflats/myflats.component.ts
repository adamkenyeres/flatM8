import { Component, OnInit } from '@angular/core';
import {FlatService} from "../../service/FlatService";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {Flat} from "../../model/Flat";
import {HttpClient, HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-myflats',
  templateUrl: './myflats.component.html',
  styleUrls: ['./myflats.component.css']
})
export class MyflatsComponent implements OnInit {

  constructor(private flatService: FlatService, private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient) { }

  flats = [];
  noFlats = false;
  error = false;

  ngOnInit() {
    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }
    this.getFlatsForUser();
    const plus = document.getElementById('plus');

    function plusToggle() {
      plus.classList.toggle('plus--active');
    }

    plus.addEventListener('click', plusToggle);
  }

  getFlatsForUser() {
    this.noFlats = false;
    this.error = false;
    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.flatService.getFlatsForUser(resp["name"]).subscribe(resp => {
        if (resp != null) {

        } else {
          this.noFlats = true
        }
      }, err => {
        this.error = true;
      })
    });
    /*this.flatService.getFlatsForUser(this.app.innerdata.name).subscribe(resp => {
      console.log(resp);
    }, err => {

    });*/
  }

  addiLiveFlat() {
    this.router.navigateByUrl("createflat?type=iLive");
  }

  addiShareFlat() {
    this.router.navigateByUrl("createflat?type=iShare");
  }
}
