export class Produto {
  idproduto?: string;
  nome: string;
  preco: number | string;

  constructor(idproduto: string, nome: string, preco: number) {
    this.idproduto = idproduto;
    this.nome = nome;
    this.preco = preco;
  }
}
