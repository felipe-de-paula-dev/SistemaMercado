export class Cliente {
  idcliente?: string;
  nome: string;
  documento: string;

  constructor(idcliente: string, nome: string, documento: string) {
    this.idcliente = idcliente;
    this.nome = nome;
    this.documento = documento;
  }
}
