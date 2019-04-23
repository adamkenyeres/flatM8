import { Component, OnInit } from '@angular/core';
import {AppService} from "../../service/AppService";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {NotificationService} from "../../service/NotificationService";
import {DeleteMateRequest} from "../../model/DeleteMateRequest";
import {FlatService} from "../../service/FlatService";

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit {

  objectKeys = Object.keys;

  constructor(private app: AppService, private http: HttpClient, private router: Router,
              private flatService: FlatService, private notificationService: NotificationService) {
  }

  user = {};
  isFirstNamePressed = false;
  isLastNamePressed= false;
  isTenantTypePressed= false;
  isTenantTypeCommentsPressed= false;
  isTenantStayTypesPressed= false;
  isTenantStayTypesCommentsPressed= false;
  isAgedPressed= false;

  success = false;
  error = false;

  ngOnInit() {
    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.http.get('http://localhost:8080/getUserByEmail?email=' + resp["name"]).subscribe(data => {
        this.user = data;
        console.log(this.user);
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

}
