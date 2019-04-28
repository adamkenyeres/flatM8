import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppService} from "../../service/AppService";
import {User} from "../../model/User";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {Flat} from "../../model/Flat";
import {EntryService} from "../../service/EntryService";

@Component({
  selector: 'app-slashn',
  templateUrl: './slashn.component.html',
  styleUrls: ['./slashn.component.css']
})
export class SlashnComponent implements OnInit {

  user: User;
  entry: FlatMateEntry;
  flat: Flat;

  error: boolean;

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
    "WORK": "Room for working person",
    "UNIVERSITY": "Room for a college student",
    "BELOW_UNI": "Room for a high school student",
    "STAY_AT_HOME": "Not doing anything for the moment",
    "OTHER": "Other",
  };

  constructor(private activatedRoute: ActivatedRoute, private app: AppService, private entryService: EntryService) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['email']) {
        this.app.getUserByEmail(params['email']).subscribe(userResp => {
          this.user = <User>userResp;
        }, error => {
          this.error = true;
        })
      } else if (params['entry']) {
        this.entryService.getEntryById(params['entry']).subscribe(flatMateEntry => {
          this.entry = <FlatMateEntry>flatMateEntry;
        }, err => this.error = true);
      }
    });
  }

  ngOnInit() {
  }

}
