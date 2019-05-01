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

  updateDeleteFlatRequestWithUser(user) {
    return this.http.post('http://localhost:8080/deleteFlatRequests/updateUser/', user);
  }

  updateAddMateRequestWithUser(user) {
    return this.http.post('http://localhost:8080/addMateRequests/updateUser/', user);
  }


  updateDeleteMateRequestWithUser(user) {
    return this.http.post('http://localhost:8080/deleteMateRequests/updateUser/', user);
  }


  updateContactRequestWithUser(user) {
    return this.http.post('http://localhost:8080/contactRequests/updateUser/', user);
  }

  getAllRequests(notifications, myRequests, email) {
    this.getAddMateRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        notifications.push(req);
      }
    }, err => {
    });


    this.getMyAddMateRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        myRequests.push(req);
      }
    }, err => {
    });


    this.getDeleteMateRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        notifications.push(req);
      }
    }, err => {
    });


    this.getMyDeleteMateRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        myRequests.push(req);
      }
    }, err => {
    });


    this.getContactRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        notifications.push(req);
      }
    }, err => {
    });


    this.getMyContactRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        myRequests.push(req);
      }
    }, err => {
    });

    this.getMyDeleteFlatRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        myRequests.push(req);
      }
    }, err => {
    });


    this.getDeleteFlatRequestsForUser(email).subscribe(resp => {
      for (let req of <Object[]>resp) {
        notifications.push(req);
      }
    }, err => {
    });
  }
}
