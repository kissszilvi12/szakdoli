import { TestBed } from '@angular/core/testing';

import { CampServiceService } from './camp.service';

describe('CampServiceService', () => {
  let service: CampServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CampServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
