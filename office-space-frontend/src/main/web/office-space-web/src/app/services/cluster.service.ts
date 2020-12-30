import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TableSearchRequest } from '../dtos/tableSearch';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ClusterService {

  readonly url = `${this.storage.get(StorageService.API)}cluster`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  create(data): Observable<any> {
    return this.http.post(`${this.url}/create`, data);
  }

  getListView(data: TableSearchRequest): Observable<any> {
    return this.http.post(`${this.url}/list-view`, data);
  }

  update(data): Observable<any> {
    return this.http.patch(this.url, data);
  }
}
