import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {HttpClient} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {FlatService} from "../../service/FlatService";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {Flat} from "../../model/Flat";

@Component({
  selector: 'app-myentries',
  templateUrl: './myentries.component.html',
  styleUrls: ['./myentries.component.css']
})
export class MyentriesComponent implements OnInit {

  error: boolean = false;
  noEntries: boolean = false;
  noFlat: boolean = false;
  entries: Array<FlatMateEntry> = [];

  ROOMTYPE_CRITERIAS = {
    "NONE": "Not given",
    "PRIVATE_ROOM": "Private Room",
    "WITH_ROOMMATE": "Room with roommate"
  };

  GENDER_CRITERIAS = {
    "NONE": "Not given",
    "FEMALE": "Room for female",
    "MALE": "Room for male"
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
      this.flatService.getFlatsForUser(resp["name"]).subscribe(flatResp => {
        this.entryService.getEntriesForFlat(flatResp).subscribe(entriesResp => {
          for (let entry of <FlatMateEntry[]>entriesResp) {
            this.entries.push(entry);
            this.noEntries = false;
          }
        }, err => {
          this.noEntries = true;
        })
      }, err => {
        this.noFlat = true;
      })
    }, err => {
      this.error = true;
    });
  }

  addEntry() {
    this.router.navigateByUrl('/createentry')
  }

}
