import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import {
  LucideAngularModule,
  ShoppingCart,
  Hash,
  Plus,
  LockIcon,
  Search,
} from 'lucide-angular';
import { CriarProdutoComponent } from '../criar-produto/criar-produto.component';
import { EditarProdutoComponent } from '../editar-produto/editar-produto.component';
import Swal from 'sweetalert2';
import { ProdutoService } from '../../../services/produto/produto.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { TokenService } from '../../../services/token/token.service';
import { Produto } from '../../../models/produto/produto.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-produtos',
  imports: [
    LucideAngularModule,
    CommonModule,
    CriarProdutoComponent,
    EditarProdutoComponent,
    FormsModule,
  ],
  templateUrl: './produtos.component.html',
  styleUrl: './produtos.component.css',
})
export class ProdutosComponent {
  readonly ShoppingIcon = ShoppingCart;
  readonly Hash = Hash;
  readonly PlusIcon = Plus;
  readonly LockedIcon = LockIcon;
  readonly SearchIcon = Search;

  constructor(
    private produtoService: ProdutoService,
    private swalService: SwalService,
    private tokenService: TokenService,
  ) {}

  Produtos: Produto[] = [];
  roles: string[] = [];

  permitir = false;

  carregarProdutos() {
    this.produtoService.getProduto().subscribe((dados: any) => {
      this.Produtos = dados;
    });
  }

  ngOnInit(): void {
    this.carregarProdutos();
    this.carregarRoles();
  }

  carregarRoles() {
    this.tokenService.getRoles().subscribe((data: string[]) => {
      this.roles = data;
      this.permitir = this.roles.includes('ROLE_ADMIN');
    });
  }

  //

  criarProduto = false;

  abrirModal() {
    this.criarProduto = true;
  }

  fecharModal() {
    this.criarProduto = false;
  }

  //

  editarProduto = false;

  editarProdutoItem: any;

  abrirModalEditar(editarProduto: any) {
    this.editarProdutoItem = editarProduto;
    this.editarProduto = true;
  }

  fecharModalEditar() {
    this.editarProduto = false;
  }

  //

  async deletarProduto(idProduto: any) {
    const id: any = idProduto;
    const resultado = await this.swalService.confirm(
      'Tem certeza?',
      'Você não poderá reverter isso!',
    );
    if (resultado) {
      this.produtoService.deleteProduto(id).subscribe({
        next: (res) => {
          this.swalService.success('Deletado!', res);
          this.carregarProdutos();
        },
        error: (err) => {
          this.swalService.error('Erro!', err.error);
        },
      });
    }
  }

  //
  naoPermitido() {
    this.swalService.warning('AVISO', 'Você Não Pode Editar Isso.');
  }

  //

  search: string = '';

  searchByName() {
    this.produtoService
      .getProdutoName(this.search)
      .subscribe((data: Produto[]) => {
        this.Produtos = data;
      });
  }
}
