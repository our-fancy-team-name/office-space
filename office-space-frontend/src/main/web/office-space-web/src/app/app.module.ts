import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AuthInterceptorProviders } from '../app/interceptors/httpConfig.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DemoComponent } from './components/demo/demo.component';
import { LoginComponent } from './components/login/login.component';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DemoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthInterceptorProviders,
    LoggedInGuardInterceptor],
  bootstrap: [AppComponent]
})
export class AppModule { }
