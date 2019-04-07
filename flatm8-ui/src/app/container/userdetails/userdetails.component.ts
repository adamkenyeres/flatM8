import { Component, OnInit } from '@angular/core';
import {AppService} from "../../service/AppService";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit {

  objectKeys = Object.keys;

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
  }

  user = {};
  isFirstNamePressed = false;
  isLastNamePressed= false;
  isTenantTypePressed= false;
  isTenantTypeCommentsPressed= false;
  isTenantStayTypesPressed= false;
  isTenantStayTypesCommentsPressed= false;
  isAgedPressed= false;
  private CONST_TENANT_TYPES;
  private CONST_TENANT_STAY_TYPES;

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
    this.app.updateUser(this.user).subscribe(resp => {
      console.log(resp);
    }, err => {
      console.log(err);
    });
    window.location.reload();
  }

}
