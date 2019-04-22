import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";
import {AppService} from "./AppService";

@Injectable()
export class EntryService {

  constructor(private http: HttpClient, private app: AppService) {
  }

  createEntry(entry) {
    return this.http.post("http://localhost:8080/flatmateEntries/", entry);
  }

  getEntriesForFlat(flat) {
    return this.http.post("http://localhost:8080/flatmateEntries/getAllForFlat/", flat);
  }
}
