import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from '../api/api';

@Injectable({
  providedIn: 'root',
})
export class ExportService {
  constructor(private http: HttpClient) {}

  private apiUrl = `${apiUrl}/excel`;

  token = sessionStorage.getItem('token');

  getExcelFileClient(id: any): any {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get(this.apiUrl + `/clientes/${id}`, {
      headers,
      responseType: 'blob',
    });
  }

  getExcelFileData(dataInicio: Date, dataFim: Date): any {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this.http.get(this.apiUrl + `/vendas/${dataInicio}/${dataFim}`, {
      headers,
      responseType: 'blob',
    });
  }
}
