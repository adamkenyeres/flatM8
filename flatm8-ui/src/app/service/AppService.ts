import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AppService {

  constructor(private http: HttpClient) {
  }

  register(user): Observable<Object> {
    return this.http.post('http://localhost:8080/registration', user);

  }
  authenticate(credentials): Observable<Object> {
    return this.http.post('http://localhost:8080/signin', credentials);
  }
}
