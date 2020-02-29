import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DemoComponent } from './components/demo/demo.component';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';


const routes: Routes = [
  { path: 'demo', component: DemoComponent, canActivate: [LoggedInGuardInterceptor] },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: 'demo', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
