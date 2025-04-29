import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ArrowLeftCircle, LucideAngularModule, X, File } from 'lucide-angular';
import { ClienteService } from '../../../services/cliente/cliente.service';
import { Cliente } from '../../../models/cliente/cliente.model';
import { ExportService } from '../../../services/export/export.service';
import { SwalService } from '../../../services/swal/swal-service.service';

@Component({
  selector: 'app-exportar-venda',
  standalone: true,
  imports: [LucideAngularModule, FormsModule, CommonModule],
  templateUrl: './exportar-venda.component.html',
  styleUrl: './exportar-venda.component.css',
})
export class ExportarVendaComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly pdfIcon = File;
  readonly excelIcon = File;

  constructor(
    private clienteService: ClienteService,
    private exportService: ExportService,
    private swalService: SwalService,
  ) {}

  clientes: Cliente[] = [];

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe((data: any) => {
      this.clientes = data;
    });
  }

  @Output() fechar = new EventEmitter<void>();

  fecharModal() {
    this.fechar.emit();
  }

  filtroSelecionado: string = '';
  arquivo: string = '';

  selecionarTipoArquivo(arquivo: string) {
    this.arquivo = arquivo;
  }

  selecionarFiltro(filtro: string) {
    this.filtroSelecionado = filtro;
  }

  //

  clienteSelecionado: Cliente | null = null;

  datainicio!: Date;
  datafim!: Date;

  exportar() {
    if (this.filtroSelecionado == 'cliente') {
      this.exportService
        .getExcelFileClient(this.clienteSelecionado?.idcliente)
        .subscribe({
          next: (file: any) => {
            const link = document.createElement('a');
            const url = window.URL.createObjectURL(file);
            link.href = url;
            link.download = `Relatorio-Vendas-${this.clienteSelecionado?.nome}.xlsx`;
            link.click();
            window.URL.revokeObjectURL(url);
          },
          error: (err: any) => {
            this.swalService.error('Erro!', 'Nenhuma Venda Encontrada');
          },
        });
    } else if (this.filtroSelecionado == 'periodo') {
      this.exportService
        .getExcelFileData(this.datainicio, this.datafim)
        .subscribe({
          next: (file: any) => {
            const link = document.createElement('a');
            const url = window.URL.createObjectURL(file);
            link.href = url;
            link.download = `Relatorio-Vendas-Periodo.xlsx`;
            link.click();
            window.URL.revokeObjectURL(url);
          },
          error: (err: any) => {
            this.swalService.error('Erro!', 'Nenhuma Venda Encontrada');
          },
        });
    }
  }
}
