import { TestBed } from '@angular/core/testing';

import { SwalService } from './swal-service.service';

describe('SwalServiceService', () => {
  let service: SwalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SwalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
