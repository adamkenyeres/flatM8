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

  flat: Object;
  noFlats = false;
  error = false;
  addMateError = false;
  deleteMateError = false;
  addingNewMate = false;
  newFlatMateEmail: string;

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

  deleteFlat() {
    this.flatService.deleteFlat(this.flat).subscribe(resp => {
      console.log("delete success");
    });
    this.flat = null;
    window.location.reload();
  }
  getFlatsForUser() {
    this.noFlats = false;
    this.error = false;
    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.flatService.getFlatsForUser(resp["name"]).subscribe(resp => {
        if (resp != null) {
          console.log(resp);
          this.flat = resp;
        }
      }, err => {
        if (err.status == 404) {
          this.noFlats = true;
        } else {
          this.error = true;
        }
      })
    });
  }

  addiLiveFlat() {
    this.router.navigateByUrl("createflat?type=iLive");
  }

  addiShareFlat() {
    this.router.navigateByUrl("createflat?type=iShare");
  }

  deleteMate(email) {
    let flatMates = this.flat["flatMates"];
    let filteredMates = flatMates.filter(mate => mate["email"] != email);
    this.flat["flatMates"] = filteredMates;

    this.flatService.addFlat(<Flat>this.flat).subscribe(resp => {
      console.log(resp);
    }, err => {
      console.log(err);
    })
  }

  addMate() {
    let coll = this.flat["flatMates"].filter(mate => mate["email"] == this.newFlatMateEmail);
    if (coll.length != 0) {
      this.addMateError = true;
      return;
    }

    this.app.getUserByEmail(this.newFlatMateEmail).subscribe(resp => {
      let flatMates = this.flat["flatMates"];
      flatMates.push(resp);
      this.flat["flatMates"] = flatMates;
      this.flatService.addFlat(<Flat>this.flat).subscribe(resp => {
        console.log(resp);
        this.addMateError = false;
      }, err => {
        console.log(err);
        this.addMateError = true;
      });
    }, err => {
      this.addMateError = true;
    });

    this.stopAddingNewFlatmate();
  }

  addNewMate() {
    this.addingNewMate = true;
  }

  stopAddingNewFlatmate() {
    this.addingNewMate = false;
  }
}
