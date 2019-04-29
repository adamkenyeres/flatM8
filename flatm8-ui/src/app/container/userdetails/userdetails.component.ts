import { Component, OnInit } from '@angular/core';
import {AppService} from "../../service/AppService";
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {NotificationService} from "../../service/NotificationService";
import {DeleteMateRequest} from "../../model/DeleteMateRequest";
import {FlatService} from "../../service/FlatService";
import {UploadFileService} from "../../service/UploadFileService";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit {

  objectKeys = Object.keys;

  constructor(private app: AppService, private http: HttpClient, private router: Router,
              private flatService: FlatService, private notificationService: NotificationService,
              private uploadService: UploadFileService, private sanitizer: DomSanitizer) {
  }

  user = {};
  userAvatar;
  isFirstNamePressed = false;
  isLastNamePressed= false;
  isTenantTypePressed= false;
  isTenantTypeCommentsPressed= false;
  isTenantStayTypesPressed= false;
  isTenantStayTypesCommentsPressed= false;
  isAgedPressed= false;

  success = false;
  error = false;
  avatarError = false;
  selectedImage: string;
  imageType = "data:image/JPEG;base64,";
  ngOnInit() {
    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.http.get('http://localhost:8080/getUserByEmail?email=' + resp["name"]).subscribe(data => {
        this.user = data;
        console.log(this.user);
        this.getAvatar();
      });
    });
  }

  editAge() {
    this.isAgedPressed = !this.isAgedPressed;
  }

  editTenantStayTypeComment() {
    this.isTenantStayTypesCommentsPressed = !this.isTenantStayTypesCommentsPressed;
  }

  editTenantStayType() {
    this.isTenantStayTypesPressed = !this.isTenantStayTypesPressed;
  }
  editTenantTypeComment() {
    this.isTenantTypeCommentsPressed = !this.isTenantTypeCommentsPressed;
  }

  editTenantType() {
    this.isTenantTypePressed = !this.isTenantTypePressed;
  }

  editLastName() {
    this.isLastNamePressed = !this.isLastNamePressed;
  }

  editFirstName() {
    this.isFirstNamePressed = !this.isFirstNamePressed;
  }

  update() {
    this.success = false;
    this.error = false;
    this.app.updateUser(this.user).subscribe(resp => {
      this.flatService.updateUserInFlat(this.user).subscribe( resp => {
      }, err => { });
      this.notificationService.updateDeleteFlatRequestWithUser(this.user).subscribe();
      this.notificationService.updateContactRequestWithUser(this.user).subscribe();
      this.notificationService.updateDeleteMateRequestWithUser(this.user).subscribe();
      this.notificationService.updateAddMateRequestWithUser(this.user).subscribe();
      this.success = true;
      window.location.reload();
    }, err => {
      this.error = true;
    });
  }

  selectedFile: File;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  selectFile(event) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile);
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFile;
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.selectedFile = undefined;
    this.user['avatarPath'] = this.currentFileUpload.name;
    this.app.updateUser(this.user).subscribe(resp => {
      this.getAvatar();
      this.currentFileUpload = null;
    });
  }

  getAvatar() {
    this.avatarError = false;
    this.app.getUserAvatar().subscribe(resp => {
      if (resp['content']) {
        this.userAvatar = this.sanitizer.bypassSecurityTrustUrl(this.imageType + resp['content']);
      }
    }, err => {
      this.avatarError = true;
    });
  }
}
