import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TableSearchRequest } from '../dtos/tableSearch';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  readonly url = `${this.storage.get(StorageService.API)}product`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  create(data): Observable<any> {
    return this.http.post(`${this.url}/create`, data);
  }

  findAll(data: TableSearchRequest): Observable<any> {
    return this.http.post(`${this.url}/list`, data);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }

  update(data): Observable<any> {
    return this.http.post(`${this.url}/update`, data);
  }
}
