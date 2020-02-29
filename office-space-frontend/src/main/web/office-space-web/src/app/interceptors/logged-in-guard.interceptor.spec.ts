import { TestBed } from '@angular/core/testing';

import { LoggedInGuardInterceptor } from './logged-in-guard.interceptor';

describe('LoggedInGuardInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      LoggedInGuardInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: LoggedInGuardInterceptor = TestBed.inject(LoggedInGuardInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
