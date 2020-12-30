import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  getUserRoleListView(table): Observable<any> {
    return this.http.post(`${this.url}/list-role`, table);
  }

  getUserDetails(id: number) {
    return this.http.get(`${this.url}/${id}`);
  }

  update(data) {
    return this.http.post(`${this.url}/update`, data);
  }

  deleteUser(id) {
    return this.http.delete(`${this.url}/${id}`);
  }

  createUser(data) {
    return this.http.post(`${this.url}/create`, data);
  }
}
