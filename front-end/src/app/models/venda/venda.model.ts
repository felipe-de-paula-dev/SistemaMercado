import { ItemVenda } from '../itemvenda/itemVenda.model';

export class Venda {
  idvenda: string;
  data: Date;
  total: number;
  venda: ItemVenda[];

  constructor(idvenda: string, data: Date, total: number, venda: ItemVenda[]) {
    this.idvenda = idvenda;
    this.data = data;
    this.total = total;
    this.venda = venda;
  }
}
