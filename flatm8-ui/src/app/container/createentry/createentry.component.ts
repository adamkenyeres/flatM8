import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {User} from "../../model/User";
import {HttpClient} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {FlatService} from "../../service/FlatService";
import {Flat} from "../../model/Flat";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {RoomCriteria} from "../../model/RoomCriteria";
import {BaseCriteria} from "../../model/BaseCriteria";
import {Options} from "ng5-slider";

@Component({
  selector: 'app-createentry',
  templateUrl: './createentry.component.html',
  styleUrls: ['./createentry.component.css']
})
export class CreateentryComponent implements OnInit {

  flats = [];
  selectedFlat: Flat;
  userEmail: string;
  noFlats: boolean = false;
  error: boolean = false;
  flatFull: boolean = false;
  objectKeys = Object.keys;
  selectedAge: number;
  selectedGender: string;
  selectedLifeStyle: string;
  selectedRoomType: string;
  selectedNumber: number;

  mainUserError: boolean = false;
  ageOffset: number = 10;
  currentEntries: Array<FlatMateEntry>;
  entryCreated: boolean = false;

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

  options: Options = {
    floor: 1,
    ceil: 30
  };

  constructor(private entryService: EntryService,private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient, private flatService: FlatService) { }

  ngOnInit() {

    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }

    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.userEmail = resp["name"];
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
    }, err => {
      this.error = true;
    });

  }

  update(){
    this.flatFull = false;
    this.mainUserError = this.userEmail !== this.selectedFlat.userEmail;

    if (this.selectedFlat.flatMates.length >= this.selectedFlat.capacity) {
      this.flatFull = true;
    }

    this.entryService.getEntriesForFlat(this.selectedFlat).subscribe(entries => {
      this.currentEntries = <FlatMateEntry[]>entries;
    });
  }

  createEntry() {
    this.entryCreated = false;
    let entry = this.assembleCreateEntry(this.selectedFlat);
    this.entryService.createEntry(entry).subscribe(resp => {
      this.entryCreated = true;
    }, err => {
      this.error = true;
    })
  }

  assembleCreateEntry(flat: Flat) {
    let e = new FlatMateEntry();
    e.flat = flat;

    let bc = new BaseCriteria();
    bc.ageCriteria = this.selectedAge;
    bc.genderCriteria = this.selectedGender;
    bc.lifestyleCriteria = this.selectedLifeStyle;
    bc.roomTypeCriteria = this.selectedRoomType;
    bc.ageOffset = this.ageOffset;

    let c = new RoomCriteria();
    c.capacity = this.selectedNumber;
    c.criteria = bc;

    e.roomCriteria = c;

    return e;
  }
}
