import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class NodeService {

  readonly url = `${this.storage.get(StorageService.API)}node`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  create(data): Observable<any> {
    return this.http.post(`${this.url}/create`, data);
  }
}
