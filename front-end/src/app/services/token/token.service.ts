import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor(private http: HttpClient) {}

  private apiUrl = 'https://backendmercado.onrender.com/auth/token';

  token = sessionStorage.getItem('token');

  validarToken() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get(this.apiUrl + '/validar', {
      responseType: 'text',
      headers,
    });
  }

  getRoles(): Observable<string[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<string[]>(this.apiUrl + '/roles', {
      headers,
    });
  }
}
