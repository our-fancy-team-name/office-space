import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TableSearchRequest } from '../dtos/tableSearch';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  readonly url = `${this.storage.get(StorageService.API)}user`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  getAllUsers() {
    return this.http.post(this.url, new TableSearchRequest());
  }

  getUserRoleListView(table) {
    return this.http.post(`${this.url}/list-role`, table);
  }
}
