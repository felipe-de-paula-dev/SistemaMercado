import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../../models/cliente/cliente.model';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private apiUrl = 'https://backendmercado.onrender.com/api/cliente';

  token = sessionStorage.getItem('token');

  constructor(private http: HttpClient) {}

  getClientesByName(name: string): Observable<Cliente[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<Cliente[]>(this.apiUrl + `/search?name=${name}`, {
      headers,
    });
  }

  getClientes(): any {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get(this.apiUrl, { headers });
  }

  postClientes(cliente: Cliente): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.post(this.apiUrl, cliente, {
      responseType: 'text',
      headers,
    });
  }

  deleteClientes(id: any): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.delete(this.apiUrl + `/${id}`, {
      responseType: 'text',
      headers,
    });
  }

  putClientes(cliente: any): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.put(this.apiUrl, cliente, {
      responseType: 'text',
      headers,
    });
  }

  validarCliente(cliente: Cliente): string | null {
    if (cliente.nome == '' || cliente.documento == '') {
      return 'O nome e o documento são obrigatórios.';
    }

    if (!isNaN(Number(cliente.nome))) {
      return 'O nome não pode ser apenas números.';
    }

    return null;
  }
}
