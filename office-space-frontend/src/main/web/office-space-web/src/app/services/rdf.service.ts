import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class RdfService {

  readonly url = `${this.storage.get(StorageService.API)}rdf`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  getDefinedNamespace(): Observable<any> {
    return this.http.get(`${this.url}/namespace`);
  }

}
