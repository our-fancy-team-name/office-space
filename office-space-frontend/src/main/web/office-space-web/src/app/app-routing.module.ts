import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DemoComponent } from './components/demo/demo.component';
import { LoginComponent } from './components/login/login.component';
import { RoleSelectComponent } from './components/role-select/role-select.component';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';


const routes: Routes = [
  { path: 'demo', component: DemoComponent, canActivate: [LoggedInGuardInterceptor] },
  { path: 'login', component: LoginComponent },
  { path: 'select-role', component: RoleSelectComponent, canActivate: [LoggedInGuardInterceptor] },
  { path: '**', redirectTo: 'demo', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
