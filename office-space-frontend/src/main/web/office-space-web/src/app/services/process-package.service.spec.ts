import { TestBed } from '@angular/core/testing';

import { ProcessPackageService } from './process-package.service';

describe('ProcessPackageService', () => {
  let service: ProcessPackageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcessPackageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
