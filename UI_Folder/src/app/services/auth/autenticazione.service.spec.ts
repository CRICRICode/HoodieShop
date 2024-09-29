import { TestBed } from '@angular/core/testing';

import { AutenticazioneService } from './autenticazione.service';

describe('AutenticazioneService', () => {
  let service: AutenticazioneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutenticazioneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
