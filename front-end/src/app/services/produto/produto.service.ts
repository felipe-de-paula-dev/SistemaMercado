import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../../models/produto/produto.model';

@Injectable({
  providedIn: 'root',
})
export class ProdutoService {
  private apiUrl = 'https://backendmercado.onrender.com/api/produto';

  constructor(private http: HttpClient) {}

  token = sessionStorage.getItem('token');

  getProdutoName(nome: String): Observable<Produto[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<Produto[]>(this.apiUrl + `/search?nome=${nome}`, {
      headers,
    });
  }

  getProduto(): Observable<Produto[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<Produto[]>(this.apiUrl, { headers });
  }

  postProduto(produto: Produto): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.post(this.apiUrl, produto, {
      responseType: 'text',
      headers,
    });
  }

  putProduto(produto: Produto): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.put(this.apiUrl, produto, {
      responseType: 'text',
      headers,
    });
  }

  deleteProduto(id: any): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.delete(this.apiUrl + `/${id}`, {
      responseType: 'text',
      headers,
    });
  }

  validarProduto(produto: Produto): string | null {
    if (!produto.nome || produto.nome.trim() === '') {
      return 'O nome do produto é obrigatório.';
    }

    if (!produto.preco || Number(produto.preco) <= 0) {
      return 'O preço do produto deve ser maior que zero.';
    }

    return null;
  }
}
