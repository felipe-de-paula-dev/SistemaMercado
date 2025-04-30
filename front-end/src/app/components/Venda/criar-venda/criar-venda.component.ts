import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

import {
  ArrowLeftCircle,
  LucideAngularModule,
  X,
  User,
  IdCard,
  Calendar,
  Hash,
  Plus,
  Table,
  Banknote,
  CreditCard,
  Smartphone,
  Loader2,
} from 'lucide-angular';
import { ProdutoService } from '../../../services/produto/produto.service';
import { ClienteService } from '../../../services/cliente/cliente.service';
import { Produto } from '../../../models/produto/produto.model';
import { ItemVendaDTO } from '../../../models/itemvenda/itemVendaDTO.model';
import { VendaDTO } from '../../../models/venda/vendaDTO.model';
import { VendaService } from '../../../services/venda/venda/venda.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { Cliente } from '../../../models/cliente/cliente.model';

@Component({
  selector: 'app-criar-venda',
  standalone: true,
  imports: [LucideAngularModule, CommonModule, FormsModule],
  templateUrl: './criar-venda.component.html',
  styleUrl: './criar-venda.component.css',
})
export class CriarVendaComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly UserIcon = User;
  readonly CardIcon = IdCard;
  readonly DateIcon = Calendar;
  readonly HashIcon = Hash;
  readonly AddIcon = Plus;
  readonly TableIcon = Table;
  readonly MoneyIcon = Banknote;
  readonly CardCredit = CreditCard;
  readonly PhoneIcon = Smartphone;
  readonly LoadIcon = Loader2;

  constructor(
    private vendaService: VendaService,
    private produtoService: ProdutoService,
    private clienteService: ClienteService,
    private swalService: SwalService,
  ) {}

  data: Date = new Date();

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  fecharModal() {
    this.fechar.emit();
  }

  totalVenda: number = 0;
  valorRecebido: number = 0;
  troco: number | null = null;

  Produtos: Produto[] = [];

  Cliente!: Cliente[];

  IdSelecionado: string = 'Selecione Um Cliente';

  itens: any = [];

  produtoNovo: any = null;

  venda: VendaDTO = {
    idcliente: this.IdSelecionado,
    itensvenda: this.itens,
  };

  //

  desabilitar = false;

  //

  getClientes() {
    this.clienteService.getClientes().subscribe((data: any) => {
      this.Cliente = data;
    });
  }

  getProdutos() {
    this.produtoService.getProduto().subscribe((data: any) => {
      this.Produtos = data;
    });
  }

  enviarVenda() {
    const error = this.vendaService.validarVendaDto(this.venda);

    if (error) {
      this.swalService.error('Erro!', error);
      return;
    }

    this.desabilitar = true;
    this.vendaService.postVendas(this.venda).subscribe({
      next: (res) => {
        this.swalService.success('Venda Salva!', res);
        this.fecharModal();
        this.atualizar.emit();
        this.exibirTroco = false;
        this.desabilitar = false;
      },
      error: (err) => {
        this.swalService.error('Erro!', err);
        this.desabilitar = false;
      },
    });
  }

  itemNovo: ItemVendaDTO = {
    nome: '',
    preco: 0,
    idproduto: '',
    qtde: 'Quantidade',
    subtotal: 0,
  };

  addItens() {
    if (this.produtoNovo != null) {
      this.itemNovo.idproduto = this.produtoNovo.idproduto || '';
      this.itemNovo.nome = this.produtoNovo.nome;
      this.itemNovo.preco = this.produtoNovo.preco;
    }

    this.itemNovo.subtotal = this.vendaService.calcularSubtotal(this.itemNovo);

    const erro = this.vendaService.validarItemDto(this.itemNovo);

    if (erro) {
      this.swalService.error('Erro!', erro);
      return;
    }

    this.venda.idcliente = this.IdSelecionado;

    this.itens.push({ ...this.itemNovo });

    this.produtoNovo = null;
    this.itemNovo.qtde = 'Quantidade';
  }

  removerItem(Item: Produto) {
    const index = this.itens.findIndex(
      (item: Produto) => item.idproduto === Item.idproduto,
    );
    if (index !== -1) {
      this.itens.splice(index, 1);
    }
  }

  ngOnInit(): void {
    this.getClientes();
    this.getProdutos();
  }

  //

  tipoPagamento = false;

  exibirTroco = false;

  avancarPagamento() {
    const error = this.vendaService.validarVendaDto(this.venda);

    if (error) {
      this.swalService.error('Erro!', error);
      return;
    }
    this.tipoPagamento = true;
  }

  avancarTroco() {
    for (const i in this.itens) {
      this.totalVenda += this.itens[i].subtotal;
    }
    this.exibirTroco = true;
    this.tipoPagamento = false;
    this.totalVenda = parseFloat(this.totalVenda.toFixed(2));
    this.totalVenda = Number(this.totalVenda);
  }

  calcularTroco() {
    this.troco = this.vendaService.calcularTroco(
      this.totalVenda,
      this.valorRecebido,
    );
  }
}
