import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SessionStorageService {
  constructor() {}

  salvar(key: any, body: any): void {
    sessionStorage.setItem(key, body);
  }

  get(key: any): any {
    return sessionStorage.getItem(key);
  }

  delete(key: any): void {
    sessionStorage.removeItem(key);
  }
}
