import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  readonly url = `${this.storage.get(StorageService.API)}log`;
  constructor(private http: HttpClient, private storage: StorageService) { }

  insertLog(log): Observable<any> {
    return this.http.post(`${this.url}/insertLog`, log);
  }
}
