import { TestBed } from '@angular/core/testing';

import { TranslationInterceptor } from './translation.interceptor';

describe('TranslationInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      TranslationInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: TranslationInterceptor = TestBed.inject(TranslationInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
