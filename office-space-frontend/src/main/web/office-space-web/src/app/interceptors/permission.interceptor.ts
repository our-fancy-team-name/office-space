import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { StorageService } from '../services/auth/storage.service';

@Injectable()
export class PermissionInterceptor implements CanActivate {

  constructor(
    private storage: StorageService,
    private router: Router
  ) { }


  canActivate(route: ActivatedRouteSnapshot) {
    const permissionNeed: string[] = route.data.perm || [];
    const permissionHave: string[] = this.storage.get(StorageService.PERMISSION)?.split(',') || [];
    let isHavePermission = false;
    for (const perm of permissionHave) {
      if (permissionNeed.indexOf(perm) >= 0) {
        isHavePermission = true;
        break;
      }
    }
    if (isHavePermission) {
      return true;
    } else {
      this.router.navigate(['/demo']);
      return false;
    }
  }

}
