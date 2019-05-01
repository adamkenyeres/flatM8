import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UploadFileService} from "../../service/UploadFileService";

@Component({
  selector: 'list-upload',
  templateUrl: './list.upload.component.html',
  styleUrls: ['./list.upload.component.css']
})
export class ListUploadComponent implements OnInit {

  showFile = false;
  fileUploads: Observable<Object>;

  constructor(private uploadService: UploadFileService) {
  }

  ngOnInit() {
  }

  showFiles(enable: boolean) {
    this.showFile = enable;

    if (enable) {
      this.fileUploads = this.uploadService.getFiles();
    }
  }
}
