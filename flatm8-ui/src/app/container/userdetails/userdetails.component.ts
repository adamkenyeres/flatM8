import { Component, OnInit } from '@angular/core';
import {AppService} from "../../service/AppService";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit {

  constructor(private app: AppService, private http: HttpClient) { }

  user = {};

  ngOnInit() {
    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.http.get('http://localhost:8080/getUserByEmail?email=' + resp["name"]).subscribe(data => {
        this.user = data;
      });
    });
    console.log(this.user);
  }


}
