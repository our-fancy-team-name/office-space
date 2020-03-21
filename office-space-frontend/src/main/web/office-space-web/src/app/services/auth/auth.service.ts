import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private storage: StorageService) { }

  login(credentials: any): Observable<any> {

    return this.http.post(
      `${this.storage.get(StorageService.API).substring(0, this.storage.get(StorageService.API).length - 4)}auth/signin`,
      {
        username: credentials.username,
        password: credentials.password
      });
  }

  logout() {
    this.storage.clear();
    window.location.reload();
  }
}
