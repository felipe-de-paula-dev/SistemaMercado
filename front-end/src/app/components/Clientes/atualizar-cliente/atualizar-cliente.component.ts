import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  ArrowLeftCircle,
  X,
  User,
  IdCard,
  LucideAngularModule,
} from 'lucide-angular';
import { ClienteService } from '../../../services/cliente/cliente.service';
import Swal from 'sweetalert2';
import { Cliente } from '../../../models/cliente/cliente.model';
import { SwalService } from '../../../services/swal/swal-service.service';

@Component({
  selector: 'app-atualizar-cliente',
  imports: [FormsModule, LucideAngularModule],
  templateUrl: './atualizar-cliente.component.html',
  styleUrl: './atualizar-cliente.component.css',
})
export class AtualizarClienteComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly UserIcon = User;
  readonly CardIcon = IdCard;

  constructor(
    private clienteService: ClienteService,
    private swalService: SwalService,
  ) {}

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  @Input() ClienteRecebido: any;

  fecharModal() {
    this.fechar.emit();
  }

  atualizarCliente() {
    this.atualizar.emit();
  }

  cliente: Cliente = {
    nome: '',
    documento: '',
  };

  async Editar() {
    this.cliente.documento = this.cliente.documento.replace(/[./-]/g, "");

    const clienteEditado = {
      idcliente: this.ClienteRecebido.idcliente,
      nome: this.cliente.nome || this.ClienteRecebido.nome,
      documento: this.cliente.documento || this.ClienteRecebido.documento,
    };

    const error = await this.clienteService.validarCliente(clienteEditado);

    if (error) {
      this.swalService.error('Erro!', error);
      return;
    }

    this.clienteService.putClientes(clienteEditado).subscribe({
      next: (res) => {
        this.swalService.success('Atualizado!', res);
        this.atualizarCliente();
        this.fecharModal();
      },
      error: (err) => {
        this.swalService.error('Erro!', 'Documento Invalido');
      },
    });
  }
}
