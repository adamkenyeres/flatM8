import {Component, NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./container/home/home.component";
import {PagenotfoundComponent} from "./container/pagenotfound/pagenotfound.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'flatm8-ui';
}
