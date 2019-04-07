import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Flat} from "../model/Flat";

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

  deleteFlat(flat): Observable<Object> {
    return this.http.delete("http://localhost:8080/flats/"+flat.id);
  }
}
