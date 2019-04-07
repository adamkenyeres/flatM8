import { Component, OnInit } from '@angular/core';
import {EntryService} from "../../service/EntryService";
import {HttpClient} from "@angular/common/http";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-myentries',
  templateUrl: './myentries.component.html',
  styleUrls: ['./myentries.component.css']
})
export class MyentriesComponent implements OnInit {

  error: boolean = false;
  noEntries: boolean = false;
  entries;

  constructor(private entryService: EntryService,private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient) { }

  ngOnInit() {
    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }

    this.http.get('http://localhost:8080/user').subscribe(resp => {
      this.entryService.getEntriesForUser(resp["name"]).subscribe(resp => {
          console.log(resp);
          this.entries = resp;
          this.error = false;
          this.noEntries = false;
      }, err => {
        if (err.status == 404) {
          this.noEntries = true;
        } else {
          this.error = true;
        }
      })
    });
  }

  addEntry() {
    this.router.navigateByUrl('/createentry')
  }

}
