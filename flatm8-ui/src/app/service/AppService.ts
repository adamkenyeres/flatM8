import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";

@Injectable()
export class AppService {

  constructor(private http: HttpClient) {
  }

  register(user, callback) {
    this.http.post('http://localhost:8080/registration', user).subscribe(response => {
      return callback && callback();
    });

  }
  authenticate(credentials, callback) {
    this.http.post('http://localhost:8080/signin', credentials)
      .subscribe(response => {
        if (response["token"]) {
          sessionStorage.setItem("token", response["token"]);
        }
        return callback && callback();
      });
  }
}
