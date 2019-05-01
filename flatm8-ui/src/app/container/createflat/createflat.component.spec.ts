import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateflatComponent} from './createflat.component';

describe('CreateflatComponent', () => {
  let component: CreateflatComponent;
  let fixture: ComponentFixture<CreateflatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateflatComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateflatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
