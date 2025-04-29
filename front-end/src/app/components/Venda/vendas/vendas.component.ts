import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChanges } from '@angular/core';
import {
  Calendar,
  LucideAngularModule,
  CircleDollarSign,
  ShoppingCart,
  Table,
  Hash,
  LockIcon,
} from 'lucide-angular';
import { CriarVendaComponent } from '../criar-venda/criar-venda.component';
import Swal from 'sweetalert2';
import { EditarItemComponent } from '../editar-item/editar-item.component';
import { EditarVendaComponent } from '../editar-venda/editar-venda.component';
import { VendaService } from '../../../services/venda/venda/venda.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { ItemVendaService } from '../../../services/venda/itemVenda/item-venda.service';
import { TokenService } from '../../../services/token/token.service';

@Component({
  selector: 'app-vendas',
  imports: [
    CommonModule,
    LucideAngularModule,
    CriarVendaComponent,
    EditarItemComponent,
    EditarVendaComponent,
  ],
  standalone: true,
  templateUrl: './vendas.component.html',
  styleUrl: './vendas.component.css',
})
export class VendasComponent {
  readonly CalendarIcon = Calendar;
  readonly MoneyIcon = CircleDollarSign;
  readonly ShoppingCartIcon = ShoppingCart;
  readonly TableIcon = Table;
  readonly HashIcon = Hash;
  readonly LockedIcon = LockIcon;

  constructor(
    private vendaService: VendaService,
    private swalService: SwalService,
    private itemService: ItemVendaService,
    private tokenService: TokenService,
  ) {}

  cadastrarVenda: boolean = false;

  roles: string[] = [];

  permitir = false;

  carregarRoles() {
    this.tokenService.getRoles().subscribe((data: string[]) => {
      this.roles = data;
      this.permitir = this.roles.includes('ROLE_ADMIN');
    });
  }

  abrirCadastro() {
    this.cadastrarVenda = true;
  }

  fecharCadastro() {
    this.cadastrarVenda = false;
  }

  editarItem: boolean = false;

  idVendaEditar: any = null;
  idVenda: any = null;

  abrirModalEditarItem(id: any, idVenda: any) {
    this.idVendaEditar = id;
    this.idVenda = idVenda;
    this.editarItem = true;
  }

  fecharModalEditarItem() {
    this.editarItem = false;
  }

  vendaEditar: any = null;

  editarVenda: boolean = false;

  abrirEditarVenda(venda: any) {
    this.vendaEditar = venda;
    this.editarVenda = true;
  }

  fecharEditarVenda() {
    this.editarVenda = false;
  }

  @Input() id: any;

  Vendas: any = [];

  carregarVendas() {
    this.vendaService.getVendas(this.id).subscribe((data) => {
      this.Vendas = data;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['id'] && this.id != null) {
      this.carregarVendas();
      this.carregarRoles();
    }
  }

  async deletarItem(id: any, idVenda: any) {
    const response = await this.swalService.confirm(
      'Você Tem Certeza',
      'Você quer apagar o Item?',
    );

    if (response) {
      this.itemService.deleteItens(id, idVenda).subscribe({
        next: (res) => {
          this.swalService.success('Item Apagado!', res);
          this.carregarVendas();
        },
        error: (err) => {
          this.swalService.error('Erro!', err.error);
        },
      });
    }
  }

  async deletarVenda(id: any) {
    const result = await this.swalService.confirmAndWrite(
      'ATENÇÃO!',
      'TODAS os itens atribuidos a está venda serão excluidos!',
      'CONFIRMAR',
    );

    if (result) {
      this.vendaService.deleteVendas(id).subscribe({
        next: (res) => {
          this.swalService.success('Venda Excluida', res);
          this.carregarVendas();
        },
        error: (err) => {
          this.swalService.error('Erro!', err.error);
        },
      });
    }
  }

  naoPermitido() {
    this.swalService.warning('AVISO', 'Você Não Pode Editar Isso.');
  }
}
