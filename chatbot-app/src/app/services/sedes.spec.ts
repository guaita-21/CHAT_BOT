import { TestBed } from '@angular/core/testing';

import { SedesService } from './sedes';

describe('Sedes', () => {
  let service: SedesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SedesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
