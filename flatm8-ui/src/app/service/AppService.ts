import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/map';
import {User} from "../model/User";

@Injectable()
export class AppService {

  public CONST_TENANT_STAY_TYPES = {
    "NONE": "I would rather not answer.",
    "AFTERNOON": "Mostly at home in the afternoons.",
    "MORNING": "Mostly at home in the mornings.",
    "NIGHT": "Mostly at home only through nights.",
    "ALL_DAY": "Usually at home all day.",
    "OTHER": "Other, will describe in the comments."
  };

  public CONST_TENANT_TYPES = {
    "NONE": "I would rather not answer.",
    "BELOW_UNI": "Going to High school.",
    "UNIVERSITY": "Going to University/College.",
    "WORK": "Working.",
    "STAY_AT_HOME": "I am staying at home.",
    "OTHER": "Other, will describe in the comments."
  };

  constructor(private http: HttpClient) {
  }

  innerdata = {};
  register(user): Observable<Object> {
    return this.http.post('http://localhost:8080/registration', user);

  }
  authenticate(credentials): Observable<Object> {
    return this.http.post('http://localhost:8080/signin', credentials);
  }

  getUserLoggedInUser(): Observable<Object> {
    return this.http.get('http://localhost:8080/user');
  }

  getUserByEmail(email) {
    return this.http.get('http://localhost:8080/getUserByEmail?email=' + email);
  }

  updateUser(user) {
    return this.http.post('http://localhost:8080/updateUser', user);
  }

  getUserAvatar() {
    return this.http.get('http://localhost:8080/getAvatar');
  }

  getUserAvatarByUser(user) {
    return this.http.post('http://localhost:8080/getAvatarByUser', user);
  }
}
