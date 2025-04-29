import { ItemVenda } from '../itemvenda/itemVenda.model';

export class VendaUpdateDTO {
  idvenda: string;
  data: Date;
  total: number;
  idcliente: string;
  itensvenda: ItemVenda[];

  constructor(
    idvenda: string,
    data: Date,
    idcliente: string,
    total: number,
    itensvenda: ItemVenda[],
  ) {
    this.idvenda = idvenda;
    this.data = data;
    this.idcliente = idcliente;
    this.total = total;
    this.itensvenda = itensvenda;
  }
}
