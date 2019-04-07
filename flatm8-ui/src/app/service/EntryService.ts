import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";
import {AppService} from "./AppService";

@Injectable()
export class EntryService {

  constructor(private http: HttpClient, private app: AppService) {
  }

  getEntriesForUser(email) {
    return this.http.get("http://localhost:8080/getAllForMainTenant/"+email);
  }
}
