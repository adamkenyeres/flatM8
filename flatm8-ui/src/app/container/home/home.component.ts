import {Component, OnInit} from '@angular/core';
import {AppService} from "../../service/AppService";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {FlatService} from "../../service/FlatService";
import {EntryService} from "../../service/EntryService";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {User} from "../../model/User";
import {NotificationService} from "../../service/NotificationService";
import {ContactRequest} from "../../model/ContactRequest";
import {UploadFileService} from "../../service/UploadFileService";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  greeting = {};
  ageRecommendedEntries: Array<FlatMateEntry> = [];
  lifeStyleRecommendedEntries: Array<FlatMateEntry> = [];
  ultimateEntries: Array<FlatMateEntry> = [];
  loadedImages: Map<string, SafeUrl[]> = new Map<string, SafeUrl[]>();
  userHasFlat: boolean = false;
  requestCreated: boolean = false;
  error: boolean = false;
  imageType = "data:image/JPEG;base64,";
  loggedInUser: User;

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

  constructor(private app: AppService, private http: HttpClient,
              private router: Router, private auth: AuthService, private flatService: FlatService,
              private entryService: EntryService, private notificationService: NotificationService,
              private uploadService: UploadFileService, private sanitizer: DomSanitizer) {

    if (this.auth.isAuthenticated()) {
      this.app.getUserLoggedInUser().subscribe(data => {
        this.greeting = data;
        this.app.getUserByEmail(data["name"]).subscribe(user => {
          this.loggedInUser = <User>user;
        }, err => {
        });
      });
    }
  }

  authenticated(): boolean {
    return this.auth.isAuthenticated();
  }

  ngOnInit() {
    this.app.getUserLoggedInUser().subscribe(auth => {
      this.flatService.getFlatsForUser(auth["name"]).subscribe(flats => {
        this.userHasFlat = true;
      }, err => {
        if (err['status'] === 404) {
          this.userHasFlat = false;
        }
      });
      this.app.getUserByEmail(auth["name"]).subscribe(user => {
        let u = <User>user;

        this.entryService.getUltimateMatches(u.tenantType, u.age).subscribe(entries => {
          for (let e of <FlatMateEntry[]>entries) {
            this.ultimateEntries.push(e);
          }
        });

        this.entryService.getEntriesForAge(u.age).subscribe(entries => {
          for (let e of <FlatMateEntry[]>entries) {
            if (!this.arrContains(this.ultimateEntries, e)) {
              this.ageRecommendedEntries.push(e);
            }
          }
        });
        this.entryService.getEntriesForLifestyle(u.tenantType).subscribe(entries => {
          for (let e of <FlatMateEntry[]>entries) {
            if (!this.arrContains(this.ultimateEntries, e)) {
              this.lifeStyleRecommendedEntries.push(e);
            }
          }
          this.loadEntryImages();
        });
      })
    });
  }

  arrContains(arr, e): boolean {
    for (let elem of arr) {
      if (elem["id"] === e["id"]) {
        return true;
      }
    }
    return false;
  }

  requestContact(entry: FlatMateEntry) {
    let cr = this.assembleContactRequest(entry);
    this.requestCreated = false;
    this.error = false;
    this.notificationService.createContactRequestsForUser(cr).subscribe(req => {
      this.requestCreated = true;
    }, err => {
      this.error = true;
    });
  }

  assembleContactRequest(entry: FlatMateEntry): ContactRequest {
    let cr = new ContactRequest();
    cr.entry = entry;
    cr.receivers = entry.flat.flatMates;
    cr.sender = this.loggedInUser;
    cr.requestType = "CONTACT_REQUEST";
    cr.requestStatus = "PENDING";
    cr.rejecters = [];
    cr.approvers = [];
    return cr;
  }

  loadEntryImages() {
    let allEntries = [];
    this.ageRecommendedEntries.forEach(e => {
      allEntries.push(e);
    });

    this.ultimateEntries.forEach(e => {
      allEntries.push(e);
    });

    this.lifeStyleRecommendedEntries.forEach(e => {
      allEntries.push(e);
    });

    allEntries.forEach(entry => {
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
