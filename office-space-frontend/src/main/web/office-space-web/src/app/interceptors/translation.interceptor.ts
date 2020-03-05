import { Injectable } from '@angular/core';
import { MissingTranslationHandler, MissingTranslationHandlerParams } from '@ngx-translate/core';

@Injectable()
export class TranslationInterceptor implements MissingTranslationHandler {

  constructor() {}

  handle(params: MissingTranslationHandlerParams) {
    console.error(`Missing translation key: ${params.key}`);
    return params.key;
  }

}
