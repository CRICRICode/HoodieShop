import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfermedOrderComponent } from './confermed-order.component';

describe('ConfermedOrderComponent', () => {
  let component: ConfermedOrderComponent;
  let fixture: ComponentFixture<ConfermedOrderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfermedOrderComponent]
    });
    fixture = TestBed.createComponent(ConfermedOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
