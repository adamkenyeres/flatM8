import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyentriesComponent} from './myentries.component';

describe('MyentriesComponent', () => {
  let component: MyentriesComponent;
  let fixture: ComponentFixture<MyentriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyentriesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyentriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
