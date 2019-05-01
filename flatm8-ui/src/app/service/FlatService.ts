import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Flat} from "../model/Flat";
import {EntryService} from "./EntryService";

@Injectable()
export class FlatService {

  constructor(private http: HttpClient) {
  }

  getFlatsForUser(userEmail): Observable<Object> {
    return this.http.get('http://localhost:8080/flats/getForUser?email=' + userEmail);
  }

  addFlat(flat: Flat): Observable<Object> {
    return this.http.post("http://localhost:8080/flats/", flat);
  }

  updateFlat(flat: Flat): Observable<Object> {
    return this.http.post("http://localhost:8080/flats/update", flat);
  }

  deleteFlat(flat): Observable<Object> {
    return this.http.delete("http://localhost:8080/flats/" + flat.id);
  }

  updateUserInFlat(user) {
    return this.http.post("http://localhost:8080/flats/updateUserInFlat/", user);
  }
}
