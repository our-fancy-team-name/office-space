import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from './storage.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private tokenStorage: StorageService) { }

  login(credentials: any): Observable<any> {
    return this.http.post(environment.api.substring(0, environment.api.length - 4) + 'auth/signin', {
      username: credentials.username,
      password: credentials.password
    });
  }

  logout() {
    this.tokenStorage.clear();
    window.location.reload();
  }
}
