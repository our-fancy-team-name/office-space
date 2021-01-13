import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ProcessPackageService {

  readonly url = `${this.storage.get(StorageService.API)}prc-pkg`;

  constructor(private http: HttpClient, private storage: StorageService) { }

  getValidPksToAdd(clusterNodeId): Observable<any> {
    return this.http.get(`${this.url}/${clusterNodeId}`);
  }

  addPkgToCltNode(processPackageDto): Observable<any> {
    return this.http.post(`${this.url}/add`, processPackageDto);
  }
}
