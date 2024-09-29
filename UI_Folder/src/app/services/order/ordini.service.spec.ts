import { TestBed } from '@angular/core/testing';

import { OrdiniService } from './ordini.service';

describe('OrdiniService', () => {
  let service: OrdiniService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdiniService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
