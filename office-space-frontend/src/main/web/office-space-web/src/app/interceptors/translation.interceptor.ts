import { Injectable } from '@angular/core';
import { MissingTranslationHandler, MissingTranslationHandlerParams } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class TranslationInterceptor implements MissingTranslationHandler {

  constructor() {}

  handle(params: MissingTranslationHandlerParams) {
    console.error(`Missing translation key: ${params.key}`);
    return params.key;
  }

}

export const createTranslationLoader = (http: HttpClient) => {
  return new TranslateHttpLoader(http, './assets/i18n/', '/translations.json');
};
