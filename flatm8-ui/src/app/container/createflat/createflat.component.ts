import {Component, OnInit} from '@angular/core';
import {Address} from "../../model/Address";
import {Flat} from "../../model/Flat";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../model/User";
import {AppService} from "../../service/AppService";
import {FlatService} from "../../service/FlatService";

@Component({
  selector: 'app-createishare',
  templateUrl: './createflat.component.html',
  styleUrls: ['./createflat.component.css']
})
export class CreateflatComponent implements OnInit {

  objectKeys = Object.keys;
  public FLAT_TYPES = {
    "FLAT": "Flat",
    "HOUSE": "House"
  };
  private address: Address = new Address();
  private flatMates: Array<Object> = [];
  private flat: Flat = new Flat();
  private step = 0;
  private type: string;
  private addingUser: boolean = false;
  private flatMateEmail: string;
  private errorAddingUser: boolean = false;
  private flatMate;
  private mainSelectedMate: number;
  private iShareError = false;
  private userEmail: string;

  constructor(private activatedRoute: ActivatedRoute, private app: AppService, private fService: FlatService, private router: Router) {
    this.activatedRoute.queryParams.subscribe(params => {
      let type = params['type'];
      if (type === "iShare") {
        this.type = "ishare";
      } else {
        this.type = "ilive";
      }
    });

    this.app.getUserLoggedInUser().subscribe(resp => {
      this.userEmail = resp["name"];
    });

    if (this.type == "ilive") {
      this.app.getUserLoggedInUser().subscribe(resp => {
        this.app.getUserByEmail(resp["name"]).subscribe(respo => {
          this.flatMates.push(respo);
        });
      });
    }
  }

  ngOnInit() {
    this.step = 0;
  }

  incrStep() {
    this.step++;
    if (this.step > 2) {
      this.createFlat();
    }
  }

  createFlat() {
    this.flat.address = this.address;
    if (this.type === "ilive") {
      this.flat.userEmail = this.flatMates[this.mainSelectedMate]["email"];
    } else {
      this.flat.userEmail = this.userEmail;
    }
    this.flat.flatMates = this.flatMates;

    console.log(this.flat);

    this.fService.addFlat(this.flat).subscribe(resp => {
      console.log(resp);
    }, err => {
      console.log(err);
    });

    window.location.href = "/myflats";
  }

  addNewFlatMate() {
    this.addingUser = true;
  }

  stopAddingNewFlatmate() {
    this.addingUser = false;
    this.flatMateEmail = '';
  }

  createFlatMate() {
    this.errorAddingUser = false;
    this.iShareError = false;
    let fme = this.flatMateEmail;

    if (!this.iShareError) {
      this.app.getUserByEmail(this.flatMateEmail).subscribe(data => {
        this.app.getUserLoggedInUser().subscribe(resp => {
          console.log(fme);
          console.log(fme + " logged: " + resp["name"]);
          if (fme == resp["name"] && this.type == "ishare") {
            this.iShareError = true;
          } else {
            this.flatMates.push(data);
          }
        })
      }, err => {
        this.errorAddingUser = true;
      });
    }
    this.stopAddingNewFlatmate();
  }
}
