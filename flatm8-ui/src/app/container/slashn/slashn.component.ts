import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppService} from "../../service/AppService";
import {User} from "../../model/User";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {Flat} from "../../model/Flat";
import {EntryService} from "../../service/EntryService";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-slashn',
  templateUrl: './slashn.component.html',
  styleUrls: ['./slashn.component.css']
})
export class SlashnComponent implements OnInit {

  user: User;
  entry: FlatMateEntry;
  flat: Flat;

  avatarError: boolean;
  error: boolean;
  userAvatar;
  userAvatarPreview;
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

  constructor(private activatedRoute: ActivatedRoute, private app: AppService, private entryService: EntryService,
              private sanitizer: DomSanitizer) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['email']) {
        this.app.getUserByEmail(params['email']).subscribe(userResp => {
          this.user = <User>userResp;
          this.getAvatar();
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

  getAvatar() {
    console.log(this.user);
    this.avatarError = false;
    this.app.getUserAvatarByUser(this.user).subscribe(resp => {
      if (resp['content']) {
        this.userAvatar = this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']);
        this.userAvatarPreview = this.sanitizer.bypassSecurityTrustStyle('url(' + this.imageType + resp['content'] + ')');
      }
    }, err => {
      this.avatarError = true;
    });
  }

}
