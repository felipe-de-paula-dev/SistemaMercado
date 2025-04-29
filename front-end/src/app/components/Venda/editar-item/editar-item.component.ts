import { Component, EventEmitter, Input, Output } from '@angular/core';
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
import Swal from 'sweetalert2';
import { ItemVendaService } from '../../../services/venda/itemVenda/item-venda.service';
import { SwalService } from '../../../services/swal/swal-service.service';

@Component({
  selector: 'app-editar-item',
  imports: [LucideAngularModule, FormsModule],
  templateUrl: './editar-item.component.html',
  styleUrl: './editar-item.component.css',
})
export class EditarItemComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly HashIcon = Hash;
  readonly ValorIcon = DollarSign;

  constructor(
    private itemService: ItemVendaService,
    private swalService: SwalService,
  ) {}

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  fecharModal() {
    this.fechar.emit();
  }

  @Input() idItem: any;
  @Input() idVenda: any;

  quantidade: any = 'Quantidade';

  editar() {
    this.itemService
      .putItens(this.idItem, this.idVenda, this.quantidade)
      .subscribe({
        next: (res) => {
          this.swalService.success('Sucesso!', res);
          this.fecharModal();
          this.atualizar.emit();
        },
        error: (err) => {
          this.swalService.error('Erro!', 'Dado Invalido');
        },
      });
  }
}
