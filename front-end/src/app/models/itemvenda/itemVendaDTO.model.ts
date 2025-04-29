export class ItemVendaDTO {
  nome: string;
  preco: number;
  idproduto: string;
  qtde: string;
  subtotal: number;

  constructor(
    idproduto: string,
    qtde: string,
    nome: string,
    preco: number,
    subtotal: number,
  ) {
    this.nome = nome;
    this.preco = preco;
    this.idproduto = idproduto;
    this.qtde = qtde;
    this.subtotal = subtotal;
  }
}
