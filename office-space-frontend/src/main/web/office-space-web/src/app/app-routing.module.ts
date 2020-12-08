import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DemoComponent } from './components/demo/demo.component';
import { LoginComponent } from './components/login/login.component';
import { RoleManageComponent } from './components/role-manage/role-manage.component';
import { RoleSelectComponent } from './components/role-select/role-select.component';
import { PERMISSION_CODE } from './enums/permissionCode';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';
import { PermissionInterceptor } from './interceptors/permission.interceptor';
import { RoleInterceptor } from './interceptors/role.interceptor';


const routes: Routes = [
  { path: 'demo', component: DemoComponent, canActivate: [LoggedInGuardInterceptor, RoleInterceptor] },
  { path: 'login', component: LoginComponent },
  { path: 'select-role', component: RoleSelectComponent, canActivate: [LoggedInGuardInterceptor] },
  {
    path: 'role-manage', component: RoleManageComponent,
    canActivate: [LoggedInGuardInterceptor, RoleInterceptor, PermissionInterceptor],
    data: { perm: PERMISSION_CODE.ROLE_EDIT }
  },
  { path: '**', redirectTo: 'demo', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
