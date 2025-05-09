import { Component, EventEmitter, Output } from '@angular/core';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import {
  ArrowLeftCircle,
  LucideAngularModule,
  X,
  User,
  IdCard,
  Hash,
  DollarSign,
  Loader2,
} from 'lucide-angular';
import { ProdutoService } from '../../../services/produto/produto.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { Produto } from '../../../models/produto/produto.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-criar-produto',
  imports: [LucideAngularModule, FormsModule, CommonModule],
  templateUrl: './criar-produto.component.html',
  styleUrl: './criar-produto.component.css',
})
export class CriarProdutoComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly HashIcon = Hash;
  readonly ValorIcon = DollarSign;
  readonly LoadIcon = Loader2;

  produto: Produto = {
    nome: '',
    preco: 0,
  };

  desabilitar = false;

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  constructor(
    private produtoService: ProdutoService,
    private swalService: SwalService,
  ) {}

  adicionarProduto() {
    const erro = this.produtoService.validarProduto(this.produto);
    if (erro) {
      this.swalService.error('Produto Não Adicionado', erro);
      return;
    }

    this.desabilitar = true;

    this.produtoService.postProduto(this.produto).subscribe({
      next: (res) => {
        this.swalService.success('Produto Adicionado', res);
        this.fecharModal();
        this.atualizar.emit();
        this.desabilitar = false;
      },
      error: (err) => {
        this.swalService.error('Produto Não Adicionado', err.error);
        this.desabilitar = false;
      },
    });
  }

  fecharModal() {
    this.fechar.emit();
  }
}
