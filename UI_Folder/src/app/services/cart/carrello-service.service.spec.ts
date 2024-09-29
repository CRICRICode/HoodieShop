import { TestBed } from '@angular/core/testing';

import { CarrelloServiceService } from './carrello-service.service';

describe('CarrelloServiceService', () => {
  let service: CarrelloServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarrelloServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
