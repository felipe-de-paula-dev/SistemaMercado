import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import {
  LucideAngularModule,
  User,
  IdCard,
  Plus,
  LockIcon,
  Search,
} from 'lucide-angular';
import { CriarClienteComponent } from '../criar-cliente/criar-cliente.component';
import { AtualizarClienteComponent } from '../atualizar-cliente/atualizar-cliente.component';
import { ClienteService } from '../../../services/cliente/cliente.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { TokenService } from '../../../services/token/token.service';
import { FormsModule } from '@angular/forms';
import { Cliente } from '../../../models/cliente/cliente.model';

@Component({
  selector: 'app-cliente',
  imports: [
    FormsModule,
    LucideAngularModule,
    CommonModule,
    CriarClienteComponent,
    AtualizarClienteComponent,
  ],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css',
})
export class ClienteComponent {
  readonly UserIcon = User;
  readonly CardIcon = IdCard;
  readonly PlusIcon = Plus;
  readonly LockedIcon = LockIcon;
  readonly SearchIcon = Search;

  cliente: any = [];
  roles: string[] = [];

  permitir = false;

  constructor(
    private clienteService: ClienteService,
    private swalService: SwalService,
    private tokenService: TokenService,
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
    this.carregarRoles();
  }

  @Output() idEmitido = new EventEmitter<any>();

  carregarClientes() {
    this.cliente = this.clienteService.getClientes().subscribe((dados: any) => {
      this.cliente = dados;
    });
  }

  carregarRoles() {
    this.tokenService.getRoles().subscribe((data: string[]) => {
      this.roles = data;
      this.permitir = this.roles.includes('ROLE_ADMIN');
    });
  }

  enviarId(idCliente: any) {
    const id: any = idCliente;
    this.idEmitido.emit(id);
  }

  async deletarCliente(idCliente: any) {
    const response = await this.swalService.confirm(
      'Tem certeza?',
      'O Cliente será excluido!',
    );
    if (response) {
      this.clienteService.deleteClientes(idCliente).subscribe({
        next: (res) => {
          this.swalService.success('Deletado', res);
          this.carregarClientes();
        },
        error: (err) => {
          this.swalService.error('Erro ao deletar', err.error);
        },
      });
    }
  }

  //

  mostrarCriacaoCliente = false;

  abrirCriacaoUsuario() {
    this.mostrarCriacaoCliente = true;
  }

  fecharCriacaoUsuario() {
    this.mostrarCriacaoCliente = false;
  }

  //

  mostrarEditarCliente = false;

  abrirEditarCliente() {
    this.mostrarEditarCliente = true;
  }

  fecharEditarCliente() {
    this.mostrarEditarCliente = false;
  }

  //

  ClienteEditar: any = [];

  EditarCliente(Cliente: any) {
    this.ClienteEditar = Cliente;
    this.abrirEditarCliente();
  }

  //

  naoPermitido() {
    this.swalService.warning('AVISO', 'Você Não Pode Editar Isso');
  }

  //

  search: string = '';

  searchByName() {
      this.clienteService
        .getClientesByName(this.search)
        .subscribe((data: Cliente[]) => {
          this.cliente = data;
        });
    
  }
}
