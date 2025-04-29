import { Component, EventEmitter, Input, input, Output } from '@angular/core';
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
} from 'lucide-angular';
import { ProdutoService } from '../../../services/produto/produto.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { Produto } from '../../../models/produto/produto.model';

@Component({
  selector: 'app-editar-produto',
  imports: [LucideAngularModule, FormsModule],
  templateUrl: './editar-produto.component.html',
  styleUrl: './editar-produto.component.css',
})
export class EditarProdutoComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly HashIcon = Hash;
  readonly ValorIcon = DollarSign;

  constructor(
    private produtoService: ProdutoService,
    private swalService: SwalService,
  ) {}

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  @Input() editarProduto: any;

  fecharModal() {
    this.fechar.emit();
  }

  produto: Produto = {
    idproduto: '',
    nome: '',
    preco: '',
  };

  atualizarProduto() {
    this.produto.idproduto = this.editarProduto.idproduto;

    if (this.produto.nome == '') this.produto.nome = this.editarProduto.nome;
    if (this.produto.preco == '') this.produto.preco = this.editarProduto.preco;

    const error = this.produtoService.validarProduto(this.produto);
    if (error) {
      this.swalService.error('Ocorreu Um Erro', error);
      return;
    }

    this.produtoService.putProduto(this.produto).subscribe({
      next: (res) => {
        this.swalService.success('Produto Atualizado Com Sucesso', res);
        this.fecharModal();
        this.atualizar.emit();
      },
      error: (err) => {
        this.swalService.error('Produto Não Foi Atualizado', err.error);
      },
    });
  }
}
