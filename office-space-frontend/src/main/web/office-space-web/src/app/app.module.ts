import { LayoutModule } from '@angular/cdk/layout';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MissingTranslationHandler, TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AuthInterceptorProviders } from '../app/interceptors/httpConfig.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DemoComponent } from './components/demo/demo.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';
import { createTranslationLoader, TranslationInterceptor } from './interceptors/translation.interceptor';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DemoComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MDBBootstrapModule.forRoot(),
    TranslateModule.forRoot({
      missingTranslationHandler: {
        provide: MissingTranslationHandler,
        useClass: TranslationInterceptor
      },
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslationLoader,
        deps: [HttpClient]
      }
    }),
    LayoutModule,
    MaterialModule
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [
    AuthInterceptorProviders,
    LoggedInGuardInterceptor],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(translate: TranslateService) {
    translate.addLangs(['en', 'vn']);
    translate.use('en');
  }
}
