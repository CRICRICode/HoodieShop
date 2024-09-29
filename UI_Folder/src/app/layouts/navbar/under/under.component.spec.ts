import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnderComponent } from './under.component';

describe('UnderComponent', () => {
  let component: UnderComponent;
  let fixture: ComponentFixture<UnderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnderComponent]
    });
    fixture = TestBed.createComponent(UnderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
