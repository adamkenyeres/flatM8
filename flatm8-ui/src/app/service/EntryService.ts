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

  deleteAllForFlat(flat) {
    return this.http.post("http://localhost:8080/flatmateEntries/deleteAllForFlat/", flat);
  }

  deleteEntry(entry) {
    return this.http.post("http://localhost:8080/flatmateEntries/deleteEntry/", entry);
  }

  getEntriesForAge(age) {
    return this.http.get("http://localhost:8080/flatmateEntries/getEntriesForAge?age=" + age);
  }
  getEntriesForLifestyle(lifestyle) {
    return this.http.get("http://localhost:8080/flatmateEntries/getEntriesForLifeStyle?lifestyleCriteria=" + lifestyle);
  }

  getUltimateMatches(lifestyle, age) {
    return this.http.get("http://localhost:8080/flatmateEntries/getEntriesForLifeStyle?lifestyleCriteria=" + lifestyle
    + "&age="+age);
  }
}
