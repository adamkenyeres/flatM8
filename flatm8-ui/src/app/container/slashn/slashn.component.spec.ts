import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SlashnComponent } from './slashn.component';

describe('SlashnComponent', () => {
  let component: SlashnComponent;
  let fixture: ComponentFixture<SlashnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SlashnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SlashnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
