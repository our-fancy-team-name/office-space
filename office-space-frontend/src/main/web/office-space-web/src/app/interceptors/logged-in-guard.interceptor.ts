import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../services/auth/token-storage.service';

@Injectable()
export class LoggedInGuardInterceptor implements CanActivate {

  constructor(private tokenStorageService: TokenStorageService, private router: Router) { }

  canActivate() {
    if (!!this.tokenStorageService.getToken()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
