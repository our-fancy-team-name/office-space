import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { StorageService } from '../services/auth/storage.service';

@Injectable()
export class LoggedInGuardInterceptor implements CanActivate {

  constructor(private tokenStorageService: StorageService, private router: Router) { }

  canActivate() {
    if (!!this.tokenStorageService.get(StorageService.TOKEN_KEY)) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
