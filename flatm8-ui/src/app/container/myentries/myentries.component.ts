import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {HttpClient} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {FlatService} from "../../service/FlatService";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {Flat} from "../../model/Flat";
import {User} from "../../model/User";
import {UploadFileService} from "../../service/UploadFileService";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

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
  loggedInUserEmail: string;
  loadedImages: Map<string, SafeUrl[]> = new Map<string, SafeUrl[]>();

  imageType = "data:image/JPEG;base64,";
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

  constructor(private entryService: EntryService,private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient, private flatService: FlatService,
              private uploadService: UploadFileService, private sanitizer: DomSanitizer) {

  }

  ngOnInit() {
    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }

    this.app.getUserLoggedInUser().subscribe(resp => {
      this.loggedInUserEmail = resp["name"];
      this.flatService.getFlatsForUser(resp["name"]).subscribe(flatResp => {
        this.entryService.getEntriesForFlat(flatResp).subscribe(entriesResp => {
          for (let entry of <FlatMateEntry[]>entriesResp) {
            this.entries.push(entry);
            this.noEntries = false;
          }
          this.loadEntryImages();
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

  deleteEntry(entry) {
    this.entryService.deleteEntry(entry).subscribe();
    window.location.reload();
  }

  loadEntryImages() {
    this.entries.forEach(entry => {
      entry.photos.forEach(photo => {
        this.uploadService.getFileByName(photo).subscribe(resp => {
          let arr = this.loadedImages.get(entry.id);
          if (arr) {
            arr.push(this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']));
          } else {
            arr = [];
            arr.push(this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']));
          }
          this.loadedImages.set(entry.id, arr);
        })
      })
    });
  }
}
