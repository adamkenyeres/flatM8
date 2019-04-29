import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Details.UploadComponent } from './details.upload.component';

describe('Details.UploadComponent', () => {
  let component: Details.UploadComponent;
  let fixture: ComponentFixture<Details.UploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Details.UploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Details.UploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
