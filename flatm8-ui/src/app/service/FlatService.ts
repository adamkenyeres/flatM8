import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class FlatService {

  constructor(private http: HttpClient) {
  }

  getFlatsForUser(userEmail): Observable<Object> {
    return this.http.get('http://localhost:8080/flats/getForUser?email=' + userEmail);
  }
}
