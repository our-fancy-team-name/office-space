import { LayoutModule } from '@angular/cdk/layout';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MissingTranslationHandler, TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AngularResizedEventModule } from 'angular-resize-event';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AuthInterceptorProviders } from '../app/interceptors/httpConfig.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChipsComponent } from './components/chips/chips.component';
import { ClusterEditListComponent } from './components/cluster-edit-list/cluster-edit-list.component';
import { ClusterPageComponent } from './components/cluster-page/cluster-page.component';
import { DemoComponent } from './components/demo/demo.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NodeEditListComponent } from './components/node-edit-list/node-edit-list.component';
import { PakageEditListComponent } from './components/pakage-edit-list/pakage-edit-list.component';
import { ProcessGraphComponent } from './components/process-graph/process-graph.component';
import { ProductEditListComponent } from './components/product-edit-list/product-edit-list.component';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { RoleEditListComponent } from './components/role-edit-list/role-edit-list.component';
import { RoleManageComponent } from './components/role-manage/role-manage.component';
import { RoleSelectComponent } from './components/role-select/role-select.component';
import { SelectSearchComponent } from './components/select-search/select-search.component';
import { UserEditListComponent } from './components/user-edit-list/user-edit-list.component';
import { LANGUAGES } from './enums/languagesEnum';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';
import { PermissionInterceptor } from './interceptors/permission.interceptor';
import { RoleInterceptor } from './interceptors/role.interceptor';
import { createTranslationLoader, TranslationInterceptor } from './interceptors/translation.interceptor';
import { MaterialModule } from './material.module';
import { StorageService } from './services/auth/storage.service';
import { NodePickerComponent } from './components/process-graph/node-picker/node-picker.component';
import { ClusterNodeEditComponent } from './components/process-graph/cluster-node-edit/cluster-node-edit.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ClusterPackageListComponent } from './components/process-graph/cluster-package-list/cluster-package-list.component';
import { RdfPageComponent } from './components/rdf-page/rdf-page.component';
import { RdfEditListComponent } from './components/rdf-edit-list/rdf-edit-list.component';
import { NewComponentComponent } from './components/new-component/new-component.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DemoComponent,
    HeaderComponent,
    RoleSelectComponent,
    HomeComponent,
    RoleManageComponent,
    RoleEditListComponent,
    ChipsComponent,
    UserEditListComponent,
    ProductPageComponent,
    ProductEditListComponent,
    PakageEditListComponent,
    SelectSearchComponent,
    ClusterPageComponent,
    ClusterEditListComponent,
    NodeEditListComponent,
    ProcessGraphComponent,
    NodePickerComponent,
    ClusterNodeEditComponent,
    ClusterPackageListComponent,
    RdfPageComponent,
    RdfEditListComponent,
    NewComponentComponent
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
    MaterialModule,
    NgxSpinnerModule,
    NgxGraphModule,
    AngularResizedEventModule,
    NgbModule
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [
    AuthInterceptorProviders,
    LoggedInGuardInterceptor,
    RoleInterceptor,
    PermissionInterceptor
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(translate: TranslateService, storage: StorageService) {
    translate.addLangs([LANGUAGES.EN, LANGUAGES.VN]);
    translate.use(storage.get(StorageService.LANGUAGE) || LANGUAGES.EN);
  }
}
