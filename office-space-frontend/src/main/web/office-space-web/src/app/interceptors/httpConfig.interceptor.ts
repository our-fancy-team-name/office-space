import { Injectable, isDevMode } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { StorageService, StorageService as storageService } from '../services/auth/storage.service';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {

  constructor(private router: Router, private token: storageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = req;
    const token = this.token.get(storageService.TOKEN_KEY);
    const role = this.token.get(StorageService.ROLE);
    if (token != null) {
      authReq = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token) });
    }
    if (role != null) {
      authReq = authReq.clone({headers: authReq.headers.set('Role', role)});
    }
    return next.handle(authReq).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
        }
        return event;
      }),
      catchError((error: HttpErrorResponse) => {
        if (!isDevMode()) {
          console.log('error--->>>', error);
        }
        if (error.status === 401 || error.status === 403) { // wrong credential or permission
          this.token.clear();
          if (!this.router.url.includes('login')) {
            location.reload();
            this.router.navigate(['/login']);
          }
        }
        if (error.status === 417) { // already handle exception
          return throwError(error);
        }
        if (!isDevMode()) {
          alert(error.error.message); // alert error
          location.reload();
        } else {
          return throwError(error);
        }
      }));
  }
}

export const AuthInterceptorProviders = { provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true };
