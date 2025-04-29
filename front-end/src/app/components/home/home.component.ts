import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { ClienteComponent } from '../Clientes/cliente/cliente.component';
import { VendasComponent } from '../Venda/vendas/vendas.component';
import { ProdutosComponent } from '../Produto/produtos/produtos.component';
import { LoginComponent } from '../login/login.component';
import { SessionStorageService } from '../../services/sessionStorage/session-storage.service';
import { UsuariosComponent } from '../Usuarios/usuarios/usuarios.component';
import { LucideAngularModule, File } from 'lucide-angular';
import { ExportarVendaComponent } from '../exportar/exportar-venda/exportar-venda.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    ClienteComponent,
    VendasComponent,
    ProdutosComponent,
    LoginComponent,
    UsuariosComponent,
    LucideAngularModule,
    ExportarVendaComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  constructor(private sessionStorage: SessionStorageService) {}

  readonly fileIcon = File;

  idRecibido!: any;

  receber(id: any) {
    this.idRecibido = id;
  }

  liberarAcesso = false;

  ativarHome() {
    this.liberarAcesso = true;
  }

  desativarHome() {
    this.liberarAcesso = false;
    this.sessionStorage.delete('token');
    this.sessionStorage.delete('nome');
    window.location.reload();
  }

  caractereUser = '';

  ngDoCheck(): void {
    if (this.sessionStorage.get('nome'))
      this.caractereUser = this.sessionStorage.get('nome');
  }

  corSelecionada: string = '';

  ngOnInit() {
    this.corSelecionada = this.corEscuraAleatoria();
  }

  corEscuraAleatoria(): string {
    const cores: string[] = [
      '#696969',
      '#B22222',
      '#228B22',
      '#7B68EE',
      '#4169E1',
      '#FF7F24',
    ];

    const indice = Math.floor(Math.random() * cores.length);
    return cores[indice];
  }

  exportar = false;

  exportarVenda() {
    this.exportar = true;
  }

  cancelarExportacao() {
    this.exportar = false;
  }
}
