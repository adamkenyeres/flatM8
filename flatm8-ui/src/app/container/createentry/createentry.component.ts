import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {User} from "../../model/User";
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {FlatService} from "../../service/FlatService";
import {Flat} from "../../model/Flat";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {RoomCriteria} from "../../model/RoomCriteria";
import {BaseCriteria} from "../../model/BaseCriteria";
import {Options} from "ng5-slider";
import {UploadFileService} from "../../service/UploadFileService";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-createentry',
  templateUrl: './createentry.component.html',
  styleUrls: ['./createentry.component.css']
})
export class CreateentryComponent implements OnInit {

  imageType = "data:image/JPEG;base64,";
  flats = [];
  selectedFlat: Flat;
  userEmail: string;
  selectedFile: File;
  currentFileUpload: File;
  entryToBeCreated: FlatMateEntry = new FlatMateEntry();
  loadedImages = [];
  objectKeys = Object.keys;

  // Models for criteria
  baseCriteria: BaseCriteria = new BaseCriteria(10);
  roomCriteria: RoomCriteria = new RoomCriteria();

  additionalDetails = [];
  sumOfMatesWithEntries = 0;

  // Details Adding
  newDetail: string;
  detailAlreadyAdded: boolean = false;
  addingDetail = false;
  selectedNumber: number = 1;

  // Errors
  flatFull: boolean = false;
  mainUserError: boolean = false;
  entryCreated: boolean = false;
  noFlats: boolean = false;
  error: boolean = false;
  flatFullWithEntries: boolean = false;

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

  options: Options = {
    floor: 1,
    ceil: 30
  };

  resetErrors() {
    this.flatFull = false;
    this.mainUserError = false;
    this.entryCreated = false;
    this.noFlats = false;
    this.error = false;
    this.flatFull = false;
  }
  constructor(private entryService: EntryService,private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient,
              private flatService: FlatService, private uploadService: UploadFileService,
              private sanitizer: DomSanitizer) { }

  ngOnInit() {

    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }

    this.app.getUserLoggedInUser().subscribe(resp => {
      this.userEmail = resp["name"];
      this.flatService.getFlatsForUser(resp["name"]).subscribe(resp => {
        this.flats.push(<Flat>resp);
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
    this.resetErrors();

    this.sumOfMatesWithEntries = 0;
    let sum = 0;

    this.entryService.getEntriesForFlat(<Flat>this.selectedFlat).subscribe(entries => {
      for (let e of <FlatMateEntry[]>entries) {
        this.sumOfMatesWithEntries += e.roomCriteria.capacity;
        sum += e.roomCriteria.capacity;
      }
      if (sum + this.selectedFlat.flatMates.length >= this.selectedFlat.capacity) {
        this.flatFullWithEntries = true;
      }
    });

    console.log(this.userEmail);
    this.mainUserError = this.userEmail !== this.selectedFlat.userEmail;

    if (this.selectedFlat.flatMates.length >= this.selectedFlat.capacity) {
      this.flatFull = true;
    }
  }

  createEntry() {
    this.resetErrors();

    let entry = this.assembleCreateEntry(this.selectedFlat);

    console.log(entry);

    this.entryService.createEntry(entry).subscribe(resp => {
      this.entryCreated = true;
      this.router.navigateByUrl('/myentries')
    }, err => {
      this.error = true;
    })
  }

  assembleCreateEntry(flat: Flat) {
    this.entryToBeCreated.flat = flat;
    this.roomCriteria.additionalDetails = this.additionalDetails;
    this.roomCriteria.criteria = this.baseCriteria;
    this.roomCriteria.capacity = this.selectedNumber;
    this.entryToBeCreated.roomCriteria = this.roomCriteria;

    return this.entryToBeCreated;
  }

  addDetail() {
    this.addingDetail = !this.addingDetail;
  }

  stopAddingDetail() {
    this.addingDetail = false;
  }

  addToList() {
    this.resetErrors();

    this.detailAlreadyAdded = false;
    if (this.additionalDetails.indexOf(this.newDetail) === -1) {
      this.additionalDetails.push(this.newDetail);
    } else {
      this.detailAlreadyAdded = true;
    }
  }

  selectFile(event) {
    this.selectedFile = event.target.files[0];
    this.upload();
  }

  upload() {

    this.currentFileUpload = this.selectedFile;
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
    }, err => {//FIXME handle error here
    });

    this.selectedFile = undefined;

    this.entryToBeCreated.photos.push(this.currentFileUpload.name);
    this.getImages();
  }

  getImages() {
    this.loadedImages = [];
    this.entryToBeCreated.photos.forEach(photo => {
      this.uploadService.getFileByName(photo).subscribe(resp => {
        this.loadedImages.push(this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']))
      })
    });
  }
}
