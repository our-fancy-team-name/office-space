import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  readonly url = `${this.storage.get(StorageService.API)}role`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  updateRoleUser(data) {
    return this.http.post(this.url, data);
  }

  createRoleUser(data) {
    return this.http.post(`${this.url}/create`, data);
  }
}
