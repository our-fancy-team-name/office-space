import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TableSearchRequest } from '../dtos/tableSearch';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private storage: StorageService) { }

  findAllPermissionByRole(role: string): Observable<any> {
    return this.http.get(`${this.storage.get(StorageService.API)}user/${role}`);
  }

  getRoleUserListView(request: TableSearchRequest): Observable<any> {
    return this.http.post(`${this.storage.get(StorageService.API)}user/role-list`, new TableSearchRequest());
  }

  getAllUsers() {
    return this.http.post(`${this.storage.get(StorageService.API)}user`, new TableSearchRequest());
  }
}
