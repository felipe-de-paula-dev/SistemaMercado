import { Produto } from '../produto/produto.model';

export class ItemVenda {
  iditemvenda?: string;
  data?: Date;
  subtotal: number;
  qtde: number | string;
  produto?: Produto;

  constructor(
    iditemvenda: string,
    data: Date,
    subtotal: number,
    qtde: number,
    produto?: Produto,
  ) {
    this.iditemvenda = iditemvenda;
    this.data = data;
    this.subtotal = subtotal;
    this.qtde = qtde;
    this.produto = produto;
  }
}
