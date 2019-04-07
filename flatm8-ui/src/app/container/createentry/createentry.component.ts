import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {User} from "../../model/User";
import {HttpClient} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {FlatService} from "../../service/FlatService";
import {Flat} from "../../model/Flat";

@Component({
  selector: 'app-createentry',
  templateUrl: './createentry.component.html',
  styleUrls: ['./createentry.component.css']
})
export class CreateentryComponent implements OnInit {

  flats = [];
  selectedFlat: Flat;
  user: User;
  noFlats: boolean = false;
  error: boolean = false;
  flatFull: boolean = false;
  objectKeys = Object.keys;

  ROOMTYPE_CRITERIAS = {
    "NONE": "Not given",
    "PRIVATE_ROOM": "Private Room",
    "WITH_ROOMMATE": "Room with roommate"
  };

  GENDER_CRITERIAS = {
    "NONE": "Not given",
    "FEMALE": "Room for female",
    "Male": "Room for male"
  };

  LIFESTYLE_CRITERIAS = {
    "NONE": "Not given",
    "WORKING": "Room for working person",
    "UNI_STUDYING": "Room for a college student",
    "BELOW_UNI_STUDYING": "Room for a high school student"
  };

  constructor(private entryService: EntryService,private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient, private flatService: FlatService) { }

  ngOnInit() {

    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }

    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.flatService.getFlatsForUser(resp["name"]).subscribe(resp => {
        this.flats.push(<Flat>resp);
        this.error = false;
        this.noFlats = false;
      }, err => {
        if (err.status == 404) {
          this.noFlats = true;
        } else {
          this.error = true;
        }
      })
    });

  }

  update(){
    console.log(this.selectedFlat);
    this.flatFull = false;
    if (this.selectedFlat.flatMates.length >= this.selectedFlat.capacity) {
      this.flatFull = true;
    }
  }
}
