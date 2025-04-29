import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/user/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  token = sessionStorage.getItem('token');

  private apiUrl = 'https://backendmercado.onrender.com/';

  getUsersByName(name: string): Observable<User[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<User[]>(
      this.apiUrl + `auth/user/search?name=${name}`,
      {
        headers,
      },
    );
  }

  getUsers(): Observable<User[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<User[]>(this.apiUrl + 'auth/user', { headers });
  }

  postUsers(Usuario: User): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.post(this.apiUrl + 'auth/registrar', Usuario, {
      responseType: 'text',
      headers,
    });
  }

  deleteUsers(id: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.delete(this.apiUrl + 'auth/user' + `/${id}`, {
      responseType: 'text',
      headers,
    });
  }

  validarUsuario(login: string, password: string, role: any): string | void {
    if (login == '' || password == '' || role == '') {
      return 'Dados Invalidos';
    }
  }
}
