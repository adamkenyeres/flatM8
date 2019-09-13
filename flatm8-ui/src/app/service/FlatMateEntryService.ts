import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppService} from "./AppService";

@Injectable()
export class FlatMateEntryService {

  constructor(private http: HttpClient, private app: AppService) {
  }

  createFlatMateEntry(entry) {
    return this.http.post("localhost:8080/flatmateEntries/", entry);
  }

  getFlatMateEntriesForFlat(flat) {
    return this.http.post("localhost:8080/flatmateEntries/getAllForFlat/", flat);
  }
}
