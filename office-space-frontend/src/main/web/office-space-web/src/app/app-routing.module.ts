import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClusterPageComponent } from './components/cluster-page/cluster-page.component';
import { DemoComponent } from './components/demo/demo.component';
import { LoginComponent } from './components/login/login.component';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { RoleManageComponent } from './components/role-manage/role-manage.component';
import { RoleSelectComponent } from './components/role-select/role-select.component';
import { PERMISSION_CODE } from './enums/permissionCode';
import { LoggedInGuardInterceptor } from './interceptors/logged-in-guard.interceptor';
import { PermissionInterceptor } from './interceptors/permission.interceptor';
import { RoleInterceptor } from './interceptors/role.interceptor';

const LOGEDIN_AND_PICKED_ROLE = [
  LoggedInGuardInterceptor, RoleInterceptor
];
const PERMISSION_PAGE = [
  LoggedInGuardInterceptor, RoleInterceptor, PermissionInterceptor
];

const routes: Routes = [
  { path: 'demo', component: DemoComponent, canActivate: LOGEDIN_AND_PICKED_ROLE },
  { path: 'login', component: LoginComponent },
  { path: 'select-role', component: RoleSelectComponent, canActivate: [LoggedInGuardInterceptor] },
  {
    path: 'role-manage', component: RoleManageComponent,
    canActivate: PERMISSION_PAGE,
    data: { perm: [PERMISSION_CODE.ROLE_EDIT, PERMISSION_CODE.USER_EDIT] }
  },
  {
    path: 'product-manage', component: ProductPageComponent,
    canActivate: PERMISSION_PAGE,
    data: { perm: [PERMISSION_CODE.PRODUCT_EDIT, PERMISSION_CODE.PACKAGE_EDIT] }
  },
  {
    path: 'cluster-manage', component: ClusterPageComponent,
    canActivate: PERMISSION_PAGE,
    data: { perm: [PERMISSION_CODE.CLUSTER_EDIT, PERMISSION_CODE.NODE_EDIT] }
  },
  { path: '**', redirectTo: 'demo', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
