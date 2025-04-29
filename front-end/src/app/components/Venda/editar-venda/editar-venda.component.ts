import {
  Component,
  EventEmitter,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import Swal from 'sweetalert2';
import { v4 as uuidv4 } from 'uuid';

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
  ArrowDown,
} from 'lucide-angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Venda } from '../../../models/venda/venda.model';
import { ItemVenda } from '../../../models/itemvenda/itemVenda.model';
import { ItemVendaDTO } from '../../../models/itemvenda/itemVendaDTO.model';
import { ProdutoService } from '../../../services/produto/produto.service';
import { Produto } from '../../../models/produto/produto.model';
import { VendaService } from '../../../services/venda/venda/venda.service';
import { VendaUpdateDTO } from '../../../models/venda/venda.update.dto';
import { SwalService } from '../../../services/swal/swal-service.service';

@Component({
  selector: 'app-editar-venda',
  standalone: true,
  imports: [LucideAngularModule, CommonModule, FormsModule],
  templateUrl: './editar-venda.component.html',
  styleUrl: './editar-venda.component.css',
})
export class EditarVendaComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly UserIcon = User;
  readonly CardIcon = IdCard;
  readonly DateIcon = Calendar;
  readonly HashIcon = Hash;
  readonly AddIcon = Plus;
  readonly TableIcon = Table;
  readonly ArrowDown = ArrowDown;

  constructor(
    private produtosService: ProdutoService,
    private vendaSerice: VendaService,
    private swalSerice: SwalService,
  ) {}

  @Input() venda!: Venda;

  @Input() idCliente!: string;

  data!: Date;
  itens: ItemVenda[] = [];
  produtoNovo: Produto | null = null;
  Produtos: Produto[] = [];

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  fecharModal() {
    this.fechar.emit();
  }

  itemNovo: ItemVenda = {
    qtde: '',
    subtotal: 0,
    produto: {
      idproduto: '',
      nome: 'Produto',
      preco: 0,
    },
  };

  carregarProdutos() {
    this.produtosService.getProduto().subscribe((data: Produto[]) => {
      this.Produtos = data;
    });
  }

  ngOnInit(): void {
    this.data = this.venda.data;
    this.carregarProdutos();
    for (const Item of this.venda.venda) {
      this.itens.push({ ...Item });
    }
  }

  editarVenda() {
    let total = 0;
    for (const item of this.itens) {
      total += item.subtotal;
    }

    const newVenda: VendaUpdateDTO = {
      idvenda: this.venda.idvenda,
      data: this.venda.data!,
      total,
      idcliente: this.idCliente,
      itensvenda: this.itens,
    };

    this.vendaSerice.putVendas(newVenda).subscribe({
      next: (res) => {
        this.swalSerice.success('Venda Atualizada', res);
        this.fecharModal();
        this.atualizar.emit();
      },
      error: (err) => {
        this.swalSerice.error('Venda Não Atualizada', err.error);
      },
    });
  }

  addItens() {
    if (this.produtoNovo) {
      this.itemNovo.produto = {
        idproduto: this.produtoNovo.idproduto!,
        nome: this.produtoNovo.nome,
        preco: this.produtoNovo.preco,
      };
    }

    this.itemNovo.subtotal =
      Number(this.itemNovo.produto?.preco) * Number(this.itemNovo.qtde);

    const erro = this.vendaSerice.validarItem(this.itemNovo);

    if (erro) {
      this.swalSerice.error('Erro!', erro);
      return;
    }

    this.itens.push({ ...this.itemNovo });

    this.produtoNovo = null;
    
    this.itemNovo = {
      qtde: 0,
      subtotal: 0,
      produto: {
        idproduto: '',
        nome: '',
        preco: 0,
      },
    };
  }

  removerItem(Item: any) {
    const index = this.itens.findIndex(
      (item: any) => item.idproduto === Item.idproduto,
    );
    if (index !== -1) {
      this.itens.splice(index, 1);
    }
  }
}
