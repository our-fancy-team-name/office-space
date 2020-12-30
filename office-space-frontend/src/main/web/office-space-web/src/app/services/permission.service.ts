import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  readonly url = `${this.storage.get(StorageService.API)}permission`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  findAllPermissionByRole(role: string): Observable<any> {
    return this.http.get(`${this.url}/${role}`);
  }
}
