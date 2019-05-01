import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyflatsComponent} from './myflats.component';

describe('MyflatsComponent', () => {
  let component: MyflatsComponent;
  let fixture: ComponentFixture<MyflatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyflatsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyflatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
