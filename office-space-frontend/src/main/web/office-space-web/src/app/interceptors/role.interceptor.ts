import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { StorageService } from '../services/auth/storage.service';

@Injectable()
export class RoleInterceptor implements CanActivate {

  constructor(
    private storage: StorageService,
    private router: Router
  ) {}

  canActivate() {
    if (!!this.storage.get(StorageService.ROLE)) {
      return true;
    }
    this.router.navigate(['/select-role']);
    return false;
  }

}
