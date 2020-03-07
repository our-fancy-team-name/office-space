import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  static readonly TOKEN_KEY = 'auth-token';
  static readonly USER_KEY = 'auth-user';

  constructor() { }

  public clear() {
    window.sessionStorage.clear();
  }

  public set(token: string, value: any) {
    window.sessionStorage.removeItem(token);
    window.sessionStorage.setItem(token, value);
  }

  public get(token: string) {
    return window.sessionStorage.getItem(token);
  }
}
