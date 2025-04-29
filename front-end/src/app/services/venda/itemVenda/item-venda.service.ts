import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemVenda } from '../../../models/itemvenda/itemVenda.model';

@Injectable({
  providedIn: 'root',
})
export class ItemVendaService {
  constructor(private http: HttpClient) {}

  private apiUrl = 'https://backendmercado.onrender.com/api/item';

  token = sessionStorage.getItem('token');

  getItens(): Observable<ItemVenda[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<ItemVenda[]>(this.apiUrl, { headers });
  }

  postItens(item: ItemVenda): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.post(this.apiUrl, item, { responseType: 'text', headers });
  }

  putItens(id: string, idVenda: string, qtde: number) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.put(this.apiUrl + `/${id}/${idVenda}`, qtde, {
      responseType: 'text',
      headers,
    });
  }

  deleteItens(id: string, idVenda: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.delete(this.apiUrl + `/${id}/${idVenda}`, {
      responseType: 'text',
      headers,
    });
  }
}
