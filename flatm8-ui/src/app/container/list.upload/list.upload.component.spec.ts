import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { List.UploadComponent } from './list.upload.component';

describe('List.UploadComponent', () => {
  let component: List.UploadComponent;
  let fixture: ComponentFixture<List.UploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ List.UploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(List.UploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
