import { TestBed } from '@angular/core/testing';

import { PermissionInterceptor } from './permission.interceptor';

describe('PermissionInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      PermissionInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: PermissionInterceptor = TestBed.inject(PermissionInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
