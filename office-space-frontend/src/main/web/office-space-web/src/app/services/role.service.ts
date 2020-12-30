import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TableSearchRequest } from '../dtos/tableSearch';
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

  deleteRole(roleId: number) {
    return this.http.delete(`${this.url}/${roleId}`);
  }

  getRoleUserListView(request: TableSearchRequest): Observable<any> {
    return this.http.post(`${this.url}/list`, request);
  }

  getAllRoleCode(): Observable<any> {
    return this.http.get(`${this.url}/list-code`);
  }
}
