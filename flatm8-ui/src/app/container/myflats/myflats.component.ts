import {Component, OnInit} from '@angular/core';
import {FlatService} from "../../service/FlatService";
import {AppService} from "../../service/AppService";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {Flat} from "../../model/Flat";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {NotificationService} from "../../service/NotificationService";
import {DeleteMateRequest} from "../../model/DeleteMateRequest";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {User} from "../../model/User";
import {AddMateRequest} from "../../model/AddMateRequest";
import {DeleteFlatRequest} from "../../model/DeleteFlatRequest";

@Component({
  selector: 'app-myflats',
  templateUrl: './myflats.component.html',
  styleUrls: ['./myflats.component.css']
})
export class MyflatsComponent implements OnInit {

  flat: Object;
  noFlats = false;
  error = false;
  addMateError = false;
  deleteMateError = false;
  addingNewMate = false;
  newFlatMateEmail: string;
  cantDeleteLastFlatMate = false;
  requestSent = false;
  requestAlreadyExists = false;
  cantDeleteMainMate = false;
  notMainUserError = false;
  mainMateSuccess = false;
  iShareError = false;

  constructor(private flatService: FlatService, private app: AppService,
              private auth: AuthService, private router: Router, private http: HttpClient,
              private notificationService: NotificationService) {
  }

  resetErrors() {
    this.noFlats = false;
    this.error = false;
    this.addMateError = false;
    this.deleteMateError = false;
    this.addingNewMate = false;
    this.cantDeleteLastFlatMate = false;
    this.requestSent = false;
    this.requestAlreadyExists = false;
    this.cantDeleteMainMate = false;
    this.notMainUserError = false;
    this.mainMateSuccess = false;
    this.iShareError = false;
  }

  ngOnInit() {
    if (!this.auth.isAuthenticated()) {
      alert("Not logged in!");
      this.router.navigateByUrl('');
    }
    this.getFlatsForUser();
    const plus = document.getElementById('plus');

    function plusToggle() {
      plus.classList.toggle('plus--active');
    }

    plus.addEventListener('click', plusToggle);
  }

  deleteFlat() {
    this.resetErrors();

    this.app.getUserLoggedInUser().subscribe(loggedInMateResp => {
      this.app.getUserByEmail(loggedInMateResp["name"]).subscribe(resp => {
        let delReq = this.createDeleteFlatRequest(resp);

        let tempArr = delReq.flatToDelete.flatMates
          .filter(mate => mate["email"] === delReq.flatToDelete.userEmail);

        if (tempArr.length === 0) { // scenario for iShare
          this.app.getUserByEmail(delReq.flatToDelete.userEmail).subscribe(user => {
            delReq.receivers.push(<User>user);
            this.notificationService.createDeleteFlatRequestsForUser(delReq).subscribe(resp => {
              this.requestSent = true;
            }, err => {
              if (err.status === 400) {
                this.requestAlreadyExists = true;
              }
            });
          });
        } else {
          this.notificationService.createDeleteFlatRequestsForUser(delReq).subscribe(resp => {
            this.requestSent = true;
          }, err => {
            if (err.status === 400) {
              this.requestAlreadyExists = true;
            }
          });
        }
      }, err => {
        this.error = true;
      })
    }, err => {
      this.error = true;
    });
  }

  getFlatsForUser() {
    this.resetErrors();

    this.http.get('http://localhost:8080/user').subscribe(resp => {

      // Update users in case it was updated since last fetch
      this.flatService.getFlatsForUser(resp["name"]).subscribe(resp => {
        if (resp != null) {
          this.flat = resp;
        }
      }, err => {
        if (err.status == 404) {
          this.noFlats = true;
        } else {
          this.error = true;
        }
      })
    });
  }

  addiLiveFlat() {
    this.router.navigateByUrl("createflat?type=iLive");
  }

  addiShareFlat() {
    this.router.navigateByUrl("createflat?type=iShare");
  }


  deleteMate(email) {
    this.resetErrors();

    let flatMates = this.flat["flatMates"];
    if (flatMates.length == 1) {
      this.cantDeleteLastFlatMate = true;
      return;
    }

    if (this.flat["userEmail"] === email) {
      this.cantDeleteMainMate = true;
      return;
    }

    this.app.getUserByEmail(email).subscribe(deleteMateResp => {
      this.app.getUserLoggedInUser().subscribe(loggedInMateResp => {
        this.app.getUserByEmail(loggedInMateResp["name"]).subscribe(resp => {
          let deleteRequest = this.createDeleteMateRequest(resp, deleteMateResp);

          // For iShare its possible that the main user is not in among flatmates
          let tempArr = deleteRequest.flat.flatMates
            .filter(mate => mate["email"] === deleteRequest.flat.userEmail);

          if (tempArr.length === 0) {
            this.app.getUserByEmail(deleteRequest.flat.userEmail).subscribe(user => {
              deleteRequest.receivers.push(<User>user);
              this.notificationService.createDeleteMateRequestsForUser(deleteRequest).subscribe(resp => {
                this.requestSent = true;
              }, err => {
                if (err.status === 400) {
                  this.requestAlreadyExists = true;
                }
              });
            })
          } else {
            this.notificationService.createDeleteMateRequestsForUser(deleteRequest).subscribe(resp => {
              this.requestSent = true;
            }, err => {
              if (err.status === 400) {
                this.requestAlreadyExists = true;
              }
            });
          }
        }, err => {
          this.error = true;
          return;
        })
      }, err => {
        this.error = true;
        return;
      });
    }, err => {
      this.error = true;
    });
  }

  addMate() {
    this.resetErrors();

    let coll = this.flat["flatMates"]
      .filter(mate => mate["email"] == this.newFlatMateEmail);

    if (coll.length != 0) {
      this.addMateError = true;
      return;
    }

    if (coll.length === 0 && this.flat["userEmail"] === this.newFlatMateEmail) {
      this.iShareError = true;
      return;
    }

    this.app.getUserByEmail(this.newFlatMateEmail).subscribe(addMateResp => {
      this.app.getUserLoggedInUser().subscribe(loggedInMateResp => {
        this.app.getUserByEmail(loggedInMateResp["name"]).subscribe(resp => {
          let addRequest = this.createAddMateRequest(resp, addMateResp);

          // For iShare its possible that the main user is not in among flatmates
          let tempArr = addRequest.flat.flatMates
            .filter(mate => mate["email"] === addRequest.flat.userEmail);

          if (tempArr.length === 0) {
            this.app.getUserByEmail(addRequest.flat.userEmail).subscribe(user => {
              addRequest.receivers.push(<User>user);
              this.notificationService.createAddMateRequestsForUser(addRequest).subscribe(resp => {

                this.requestSent = true;
              }, err => {
                if (err.status === 400) {
                  this.requestAlreadyExists = true;
                }
              });
            })
          } else {
            this.notificationService.createAddMateRequestsForUser(addRequest).subscribe(resp => {

              this.requestSent = true;
            }, err => {
              if (err.status === 400) {
                this.requestAlreadyExists = true;
              }
            });
          }
        })
      })
    });

    this.stopAddingNewFlatmate();
  }

  addNewMate() {
    this.addingNewMate = true;
  }

  stopAddingNewFlatmate() {
    this.addingNewMate = false;
  }

  createDeleteMateRequest(resp, deleteMateResp): DeleteMateRequest {
    let deleteRequest = new DeleteMateRequest();
    deleteRequest.sender = <User>resp;
    deleteRequest.flat = <Flat>this.flat;
    deleteRequest.mateToDelete = <User>deleteMateResp;
    let arr = [];
    arr = arr.concat(this.flat['flatMates']);
    deleteRequest.receivers = arr;
    deleteRequest.requestStatus = "PENDING";
    deleteRequest.approvers = [];
    deleteRequest.rejecters = [];
    deleteRequest.requestType = "MATE_DELETE";

    return deleteRequest;
  }

  createAddMateRequest(resp, addMateResp) {
    let addRequest = new AddMateRequest();
    addRequest.sender = <User>resp;
    addRequest.flat = <Flat>this.flat;
    addRequest.mateToAdd = <User>addMateResp;
    let arr = [];
    arr = arr.concat(this.flat['flatMates']);
    arr.push(addRequest.mateToAdd);
    addRequest.receivers = arr;
    addRequest.requestStatus = "PENDING";
    addRequest.approvers = [];
    addRequest.rejecters = [];
    addRequest.requestType = "MATE_ADD";

    return addRequest;
  }

  createDeleteFlatRequest(resp) {
    let deleteRequest = new DeleteFlatRequest();
    deleteRequest.sender = <User>resp;
    deleteRequest.flatToDelete = <Flat>this.flat;
    let arr = [];
    arr = arr.concat(this.flat['flatMates']);
    deleteRequest.receivers = arr;
    deleteRequest.requestStatus = "PENDING";
    deleteRequest.approvers = [];
    deleteRequest.rejecters = [];
    deleteRequest.requestType = "FLAT_DELETE";

    return deleteRequest;
  }

  makeMainMate(mate: User) {
    this.resetErrors();

    this.app.getUserLoggedInUser().subscribe(resp => {
      if (resp["name"] === this.flat["userEmail"]) {
        this.flat["userEmail"] = mate.email;
        this.flatService.updateFlat(<Flat>this.flat).subscribe();
        this.mainMateSuccess = true;
      } else {
        this.notMainUserError = true;
      }
    });
  }
}
