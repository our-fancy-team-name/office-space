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
    const res = this.storage.get(StorageService.PERMISSION).split(',').indexOf(route.data.perm) >= 0;
    if (res) {
      return true;
    } else {
      this.router.navigate(['/demo']);
      return false;
    }
  }

}
