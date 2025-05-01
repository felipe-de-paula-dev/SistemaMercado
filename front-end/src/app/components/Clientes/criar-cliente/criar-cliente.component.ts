import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  ArrowLeftCircle,
  LucideAngularModule,
  X,
  User,
  IdCard,
  Loader2,
} from 'lucide-angular';
import { ClienteService } from '../../../services/cliente/cliente.service';
import Swal from 'sweetalert2';
import { SwalService } from '../../../services/swal/swal-service.service';
import { Cliente } from '../../../models/cliente/cliente.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-criar-cliente',
  imports: [LucideAngularModule, FormsModule, CommonModule],
  templateUrl: './criar-cliente.component.html',
  styleUrl: './criar-cliente.component.css',
})
export class CriarClienteComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly UserIcon = User;
  readonly CardIcon = IdCard;
  readonly LoaderIcon = Loader2;

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  constructor(
    private clienteService: ClienteService,
    private swalService: SwalService,
  ) {}

  salvando = false;

  cliente: Cliente = {
    nome: '',
    documento: '',
  };

  fecharModal() {
    this.fechar.emit();
  }

  salvar() {
    
    this.cliente.documento = this.cliente.documento.replace(/[./-]/g, "");

    this.salvando = true;

    const error = this.clienteService.validarCliente(this.cliente);
    if (error) {
      this.swalService.error('Cliente Não Adicionado', error);
      this.salvando = false;
      return;
    }


    this.clienteService.postClientes(this.cliente).subscribe({
      next: (response) => {
        this.swalService.success('Cliente Salvo', response);
        this.atualizar.emit();
        this.fecharModal();
        this.salvando = false;
      },
      error: (error) => {
        this.swalService.error('Ocorreu Um Erro', error.error);
        this.salvando = false;
      },
    });
  }
}
