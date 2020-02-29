import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../services/auth/token-storage.service';

@Injectable()
export class LoggedInGuardInterceptor implements CanActivate {
  
  constructor(private tokenStorageService: TokenStorageService, private router: Router) {}
  
  canActivate(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot): boolean | import("@angular/router").UrlTree | Observable<boolean | import("@angular/router").UrlTree> | Promise<boolean | import("@angular/router").UrlTree> {
    if(!!this.tokenStorageService.getToken()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
