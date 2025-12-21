import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from '../../models/login/login.model';
import { apiUrl } from '../api/api';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  private urlApi = `${apiUrl}/auth/login`;

  postLogin(login: Login): Observable<any> {
    return this.http.post(this.urlApi, login);
  }
}
