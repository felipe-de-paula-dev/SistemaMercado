import { ItemVendaDTO } from '../itemvenda/itemVendaDTO.model';

export class VendaDTO {
  idcliente: string;
  itensvenda: ItemVendaDTO[];

  constructor(idcliente: string, itensvenda: ItemVendaDTO[]) {
    this.idcliente = idcliente;
    this.itensvenda = itensvenda;
  }
}
