import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  readonly url = `${this.storage.get(StorageService.API)}process`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  getByClusterId(clusterId: number): Observable<any> {
    return this.http.get(`${this.url}/${clusterId}`);
  }

  addNodeToCluster(data): Observable<any> {
    return this.http.post(`${this.url}/add-node`, data);
  }

  editClusterNode(data): Observable<any> {
    return this.http.post(`${this.url}/edit-node`, data);
  }

  removeNodeFromCluster(id): Observable<any> {
    return this.http.delete(`${this.url}/remove-node/${id}`);
  }

  addPath(from, to): Observable<any> {
    return this.http.post(`${this.url}/add-path/${from}/${to}`, {});
  }

  removePath(id): Observable<any> {
    return this.http.delete(`${this.url}/remove-path/${id}`);
  }
}
