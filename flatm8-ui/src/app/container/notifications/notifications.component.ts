import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../../service/NotificationService";
import {AppService} from "../../service/AppService";
import {BaseRequest} from "../../model/BaseRequest";
import {User} from "../../model/User";
import {FlatService} from "../../service/FlatService";
import {Flat} from "../../model/Flat";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  constructor(private notificationService: NotificationService, private app: AppService, private flatService: FlatService) { }

  notifications = [];
  myRequests = [];
  error = false;
  loggedInEmail;
  requestCompleted = false;

  ngOnInit() {
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.loggedInEmail = resp["name"];
    });
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.notificationService.getAddMateRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.notifications.push(req);
        }
      }, err => {});


      this.notificationService.getMyAddMateRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.myRequests.push(req);
        }
      }, err => {});


      this.notificationService.getDeleteMateRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.notifications.push(req);
          console.log(req);
        }
      }, err => {});


      this.notificationService.getMyDeleteMateRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.myRequests.push(req);
        }
      }, err => {});


      this.notificationService.getContactRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.notifications.push(req);
        }
      }, err => {});


      this.notificationService.getMyContactRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.myRequests.push(req);
        }
      }, err => {});

      this.notificationService.getMyDeleteFlatRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.myRequests.push(req);
        }
      }, err => {});


      this.notificationService.getDeleteFlatRequestsForUser(resp["name"]).subscribe(resp => {
        for (let req of <Object[]>resp) {
          this.notifications.push(req);
        }
      }, err => {});


    }, err => {
      this.error = true;
    })
  }

  getTypeHR(noti) {
    if (noti.requestType === "MATE_DELETE") {
      return "Delete Mate From Flat";
    } else if (noti.requestType === "MATE_ADD") {
      return "Add Mate to Flat";
    } else if (noti.requestType === "CONTACT_REQUEST") {
      return "Contact Request";
    } else if (noti.requestType === "FLAT_DELETE") {
      return "Delete Flat";
    } else {
      return "Unknown";
    }
  }

  isAcceptOrRejectNeeded(notification): boolean {
    return (notification.approvers.length + notification.rejecters.length) < notification.receivers.length
          && notification.approvers.filter(u => u.email === this.loggedInEmail).length === 0
          && notification.rejecters.filter(u => u.email === this.loggedInEmail).length === 0
  }

  isRequestFulfilled(request): boolean {
    if (request.approvers.length + request.rejecters.length === request.receivers.length) {
      if (request.approvers.length > request.rejecters.length) {
        request.requestStatus = "ACCEPTED";
        return true;
      } else {
        request.requestStatus = "REJECTED";
        return true;
      }
    }
  }

  acceptRequest(request) {
    this.requestCompleted = false;
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.app.getUserByEmail(resp["name"]).subscribe(user => {
        request.approvers.push(user);
        if (request.requestType === "MATE_DELETE") {
          if (this.isRequestFulfilled(request)) {
            this.flatService.getFlatsForUser(request.mateToDelete.email).subscribe(resp => {
                let flat = <Flat>resp;
                flat.flatMates = flat.flatMates.filter(fmate => fmate.email != request.mateToDelete.email);
                this.flatService.updateFlat(flat).subscribe(resp => {
                  this.requestCompleted = true;
                });
              })
          }
          this.notificationService.updateDeleteMateRequestsForUser(request).subscribe();
        } else if (request.requestType === "CONTACT_REQUEST") {
          if (this.isRequestFulfilled(request)) {

          }
          this.notificationService.updateContactRequestsForUser(request).subscribe();
        } else if (request.requestType === "MATE_ADD") {
          if (this.isRequestFulfilled(request)) {
            this.flatService.getFlatsForUser(request.sender.email).subscribe(resp => {
              let flat = <Flat>resp;
              flat.flatMates.push(request.mateToAdd);
              this.flatService.updateFlat(flat).subscribe(resp => {
                this.requestCompleted = true;
              });
            })
          }
          this.notificationService.updateAddMateRequestsForUser(request).subscribe();
        } else if (request.requestType === "FLAT_DELETE") {
          if (this.isRequestFulfilled(request)) {
            this.flatService.deleteFlat(request.flatToDelete).subscribe(resp => {
              this.requestCompleted = true;
            }, err => {
              this.error = true;
            })
          }
          this.notificationService.updateDeleteFlatRequestsForUser(request).subscribe();
        }
      })
    });
  }

  rejectRequest(request) {
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.app.getUserByEmail(resp["name"]).subscribe(user => {
        request.rejecters.push(user);
        this.isRequestFulfilled(request);
        if (request.requestType === "MATE_DELETE") {
          this.notificationService.updateDeleteMateRequestsForUser(request).subscribe();
        } else if (request.requestType === "CONTACT_REQUEST") {
          this.notificationService.updateContactRequestsForUser(request).subscribe();
        } else if (request.requestType === "MATE_ADD") {
          this.notificationService.updateAddMateRequestsForUser(request).subscribe();
        } else if (request.requestType === "FLAT_DELETE") {
          this.notificationService.updateDeleteFlatRequestsForUser(request).subscribe();
        }
      }, err => {})
    }, error1 => {});
  }
}
