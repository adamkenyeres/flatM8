import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/Address";
import {Flat} from "../../model/Flat";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-createishare',
  templateUrl: './createflat.component.html',
  styleUrls: ['./createflat.component.css']
})
export class CreateflatComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute) { this.activatedRoute.queryParams.subscribe(params => {
    let type = params['type'];
    if (type === "iShare") {
      this.type = "ishare";
    } else {
      this.type = "ilive";
    }
  });
  }

  step = 0;
  address: Address;
  flat: Flat;
  type;

  ngOnInit() {
    this.step = 0;
  }

  incrStep() {
    this.step++;
    if (this.step > 2) {
      this.step = 0;
    }
  }
}
