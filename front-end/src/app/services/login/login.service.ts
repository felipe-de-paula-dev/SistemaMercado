import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from '../../models/login/login.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  private urlApi = 'https://backendmercado.onrender.com/auth/login';

  postLogin(login: Login): Observable<any> {
    return this.http.post(this.urlApi, login);
  }
}
