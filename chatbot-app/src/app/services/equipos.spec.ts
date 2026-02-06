import { TestBed } from '@angular/core/testing';

import { EquiposService } from './equipos';

describe('Equipos', () => {
  let service: EquiposService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquiposService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
