import { Injectable } from '@angular/core';
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
import { StorageService as storageService } from '../services/auth/storage.service';
import { map, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {

  constructor(private router: Router, private token: storageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = req;
    const token = this.token.get(storageService.TOKEN_KEY);
    if (token != null) {
      authReq = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + token) });
    }
    return next.handle(authReq).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('event--->>>', event);
        }
        return event;
      }),
      catchError((error: HttpErrorResponse) => {
        console.log('error--->>>', error);
        if (error.status === 401) {
          this.token.clear();
          this.router.navigate(['/login']);
        }
        return throwError(error);
      }));
  }
}

export const AuthInterceptorProviders = { provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true };
