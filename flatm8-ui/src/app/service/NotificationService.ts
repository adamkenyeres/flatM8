import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class NotificationService {

  constructor(private http: HttpClient) {
  }

  getAddMateRequestsForUser(user) {
    return this.http.get('http://localhost:8080/addMateRequests/getForUser?email=' + user);
  }

  getDeleteMateRequestsForUser(user) {
    return this.http.get('http://localhost:8080/deleteMateRequests/getForUser?email=' + user);
  }

  getContactRequestsForUser(user) {
    return this.http.get('http://localhost:8080/contactRequests/getForUser?email=' + user);
  }

  getDeleteFlatRequestsForUser(user) {
    return this.http.get('http://localhost:8080/deleteFlatRequests/getForUser?email=' + user);
  }

  getMyAddMateRequestsForUser(user) {
    return this.http.get('http://localhost:8080/addMateRequests/getMyRequests?email=' + user);
  }

  getMyDeleteMateRequestsForUser(user) {
    return this.http.get('http://localhost:8080/deleteMateRequests/getMyRequests?email=' + user);
  }

  getMyContactRequestsForUser(user) {
    return this.http.get('http://localhost:8080/contactRequests/getMyRequests?email=' + user);
  }

  getMyDeleteFlatRequestsForUser(user) {
    return this.http.get('http://localhost:8080/deleteFlatRequests/getMyRequests?email=' + user);
  }

  createAddMateRequestsForUser(request) {
    return this.http.post('http://localhost:8080/addMateRequests/', request);
  }

  createDeleteMateRequestsForUser(request) {
    return this.http.post('http://localhost:8080/deleteMateRequests/', request);
  }

  createContactRequestsForUser(request) {
    return this.http.post('http://localhost:8080/contactRequests/', request);
  }

  createDeleteFlatRequestsForUser(request) {
    return this.http.post('http://localhost:8080/deleteFlatRequests/', request);
  }

  updateAddMateRequestsForUser(request) {
    return this.http.post('http://localhost:8080/addMateRequests/updateRequest/', request);
  }

  updateDeleteMateRequestsForUser(request) {
    return this.http.post('http://localhost:8080/deleteMateRequests/updateRequest/', request);
  }

  updateContactRequestsForUser(request) {
    return this.http.post('http://localhost:8080/contactRequests/updateRequest/', request);
  }

  updateDeleteFlatRequestsForUser(request) {
    return this.http.post('http://localhost:8080/deleteFlatRequests/updateRequest/', request);
  }

}
