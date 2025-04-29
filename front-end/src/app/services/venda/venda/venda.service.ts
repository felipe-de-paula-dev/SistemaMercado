import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Venda } from '../../../models/venda/venda.model';
import { isEmpty, Observable } from 'rxjs';
import { VendaDTO } from '../../../models/venda/vendaDTO.model';
import { VendaUpdateDTO } from '../../../models/venda/venda.update.dto';
import { ItemVendaDTO } from '../../../models/itemvenda/itemVendaDTO.model';
import { ItemVenda } from '../../../models/itemvenda/itemVenda.model';

@Injectable({
  providedIn: 'root',
})
export class VendaService {
  constructor(private http: HttpClient) {}

  private apiUrl = 'https://backendmercado.onrender.com/api/venda';

  token = sessionStorage.getItem('token');

  getVendas(id: string): Observable<Venda[]> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get<Venda[]>(this.apiUrl + `/cliente/${id}`, { headers });
  }

  postVendas(venda: VendaDTO): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.post(this.apiUrl, venda, {
      responseType: 'text',
      headers,
    });
  }

  putVendas(venda: any): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.put(this.apiUrl, venda, { responseType: 'text', headers });
  }

  deleteVendas(id: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.delete(this.apiUrl + `/${id}`, {
      responseType: 'text',
      headers,
    });
  }

  validarVendaDto(venda: VendaDTO): string | null {
    if (venda.idcliente == '' || venda.itensvenda.length === 0) {
      return 'Existem Informações Faltando';
    }

    return null;
  }

  validarItemDto(item: ItemVendaDTO): string | null {
    if (item.nome == '' || item.qtde == '' || item.qtde == 'Quantidade') {
      return 'Prencha Os Campos Corretamente';
    }

    return null;
  }

  validarItem(item: ItemVenda): string | null {
    if (item.produto!.nome == '' || item.qtde == 0) {
      return 'Prencha Os Campos Corretamente';
    }

    return null;
  }

  calcularSubtotal(item: any): number {
    return Number(item.qtde) * item.preco;
  }

  calcularTroco(total: number, recebido: number): number {
    if (isNaN(total) || isNaN(recebido)) return 0;
    if (total > recebido) return 0;
    return recebido - total;
  }
}
