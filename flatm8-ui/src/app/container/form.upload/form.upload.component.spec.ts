import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form.UploadComponent } from './form.upload.component';

describe('Form.UploadComponent', () => {
  let component: Form.UploadComponent;
  let fixture: ComponentFixture<Form.UploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form.UploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form.UploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
