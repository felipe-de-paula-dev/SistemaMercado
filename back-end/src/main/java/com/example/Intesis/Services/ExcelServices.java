package com.example.Intesis.Services;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Venda.Venda;
import com.example.Intesis.Respositories.ClienteRepository;
import com.example.Intesis.Respositories.VendaRespository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelServices {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendaRespository vendaRespository;

    public ByteArrayInputStream exportarClientes(UUID id) throws IOException {

        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        List<Venda> vendas = vendaRespository.findAllByCliente(cliente);

        if(vendas.isEmpty()) throw new RuntimeException("Vendas Não Encontradas");

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle priceStyle = workbook.createCellStyle();
            CellStyle dateStyle = workbook.createCellStyle();
            CellStyle bold = workbook.createCellStyle();
            CellStyle style = workbook.createCellStyle();
            CellStyle bgStyle = workbook.createCellStyle();
            Font font = workbook.createFont();

            font.setBold(true);
            bold.setFont(font);

            bold.setAlignment(HorizontalAlignment.CENTER);
            bold.setVerticalAlignment(VerticalAlignment.CENTER);


            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            priceStyle.setAlignment(HorizontalAlignment.CENTER);
            priceStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            dateStyle.setAlignment(HorizontalAlignment.CENTER);
            dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            DataFormat format = workbook.createDataFormat();
            priceStyle.setDataFormat(format.getFormat("R$ #,##0.00"));
            dateStyle.setDataFormat(format.getFormat("dd/MM/yyyy"));

            bgStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

            bgStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Criando a planilha
            Sheet sheet = workbook.createSheet("Clientes");

            int rowId = 0;

            for (Venda venda : vendas) {

                // Cabeçalho 1 (Informações principais da venda)
                Row headerRow1 = sheet.createRow(rowId++);
                Cell title0 = headerRow1.createCell(0);
                title0.setCellValue("Id Venda");
                title0.setCellStyle(bold);

                Cell title1 = headerRow1.createCell(1);
                title1.setCellValue("Nome Cliente");
                title1.setCellStyle(bold);

                Cell title2 = headerRow1.createCell(2);
                title2.setCellValue("Data da Venda");
                title2.setCellStyle(bold);

                Cell title3 = headerRow1.createCell(3);
                title3.setCellValue("Valor da Venda");
                title3.setCellStyle(bold);

                // Dados da venda
                Row row = sheet.createRow(rowId++);
                Cell idCell = row.createCell(0);
                idCell.setCellValue(venda.getIdvenda().toString());
                idCell.setCellStyle(style);

                Cell nomeCell = row.createCell(1);
                nomeCell.setCellValue(venda.getCliente().getNome());
                nomeCell.setCellStyle(style);

                sheet.autoSizeColumn(1);

                Cell dataCell = row.createCell(2);
                dataCell.setCellValue(venda.getData());
                dataCell.setCellStyle(dateStyle);

                Cell totalCell = row.createCell(3);
                totalCell.setCellValue(venda.getTotal());
                totalCell.setCellStyle(priceStyle);

                // Cabeçalho 2 (Informações dos itens)
                Row headerItemRow = sheet.createRow(rowId++);
                Cell subtitle0 = headerItemRow.createCell(0);
                subtitle0.setCellValue("Produto");
                subtitle0.setCellStyle(bold);

                Cell subtitle1 = headerItemRow.createCell(1);
                subtitle1.setCellValue("Quantidade");
                subtitle1.setCellStyle(bold);

                Cell subtitle2 = headerItemRow.createCell(2);
                subtitle2.setCellValue("Data Do Item");
                subtitle2.setCellStyle(bold);

                Cell subtitle3 = headerItemRow.createCell(3);
                subtitle3.setCellValue("Subtotal");
                subtitle3.setCellStyle(bold);

                // Preenchendo os itens da venda
                for (Item_Venda item : venda.getVenda()) {
                    Row itemVendaRow = sheet.createRow(rowId++);

                    Cell produtoCell = itemVendaRow.createCell(0);
                    produtoCell.setCellValue(item.getProduto().getNome());
                    produtoCell.setCellStyle(style);

                    Cell qtdCell = itemVendaRow.createCell(1);
                    qtdCell.setCellValue(item.getQtde());
                    qtdCell.setCellStyle(style);

                    Cell datCell = itemVendaRow.createCell(2);
                    datCell.setCellValue(item.getData());
                    datCell.setCellStyle(dateStyle);

                    Cell subtotalCell = itemVendaRow.createCell(3);
                    subtotalCell.setCellValue(item.getSubtotal());
                    subtotalCell.setCellStyle(priceStyle);
                }

                // Adicionando a divisória
                Row divisoriaRow = sheet.createRow(rowId++);
                divisoriaRow.createCell(0).setCellStyle(bgStyle);
                divisoriaRow.createCell(1).setCellStyle(bgStyle);
                divisoriaRow.createCell(2).setCellStyle(bgStyle);
                divisoriaRow.createCell(3).setCellStyle(bgStyle);
            }

            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportarVendasPorPeriodo(Date dataInicial, Date dataFinal) throws IOException {

        // Recuperando as vendas dentro do período
        List<Venda> vendas = vendaRespository.findAllByDataBetween(dataInicial, dataFinal);

        if(vendas.isEmpty()) throw new RuntimeException("Vendas Não Encontradas");

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            System.out.println(dataInicial);
            System.out.println(dataFinal);
            // Definindo estilos
            CellStyle priceStyle = workbook.createCellStyle();
            CellStyle dateStyle = workbook.createCellStyle();
            CellStyle bold = workbook.createCellStyle();
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            CellStyle bgStyle = workbook.createCellStyle();


            font.setBold(true);
            bold.setFont(font);

            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            priceStyle.setAlignment(HorizontalAlignment.CENTER);
            priceStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            dateStyle.setAlignment(HorizontalAlignment.CENTER);
            dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            bold.setAlignment(HorizontalAlignment.CENTER);
            bold.setVerticalAlignment(VerticalAlignment.CENTER);

            bgStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

            bgStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            DataFormat format = workbook.createDataFormat();
            priceStyle.setDataFormat(format.getFormat("R$ #,##0.00"));
            dateStyle.setDataFormat(format.getFormat("dd/MM/yyyy"));

            // Criando a planilha
            Sheet sheet = workbook.createSheet("Vendas");

            int rowId = 0;



            // Preenchendo as vendas
            for (Venda venda : vendas) {
                Row headerRow = sheet.createRow(rowId++);
                Cell title0 = headerRow.createCell(0);
                title0.setCellValue("ID Venda");
                title0.setCellStyle(bold);

                Cell title1 = headerRow.createCell(1);
                title1.setCellValue("Nome Cliente");
                title1.setCellStyle(bold);

                Cell title2 = headerRow.createCell(2);
                title2.setCellValue("Data da Venda");
                title2.setCellStyle(bold);

                Cell title3 = headerRow.createCell(3);
                title3.setCellValue("Valor da Venda");
                title3.setCellStyle(bold);


                Row row = sheet.createRow(rowId++);
                Cell idCell = row.createCell(0);
                idCell.setCellValue(venda.getIdvenda().toString());
                idCell.setCellStyle(style);

                Cell clienteCell = row.createCell(1);
                clienteCell.setCellValue(venda.getCliente().getNome());
                clienteCell.setCellStyle(dateStyle);

                Cell dataCell = row.createCell(2);
                dataCell.setCellValue(venda.getData());
                dataCell.setCellStyle(dateStyle);

                Cell totalCell = row.createCell(3);
                totalCell.setCellValue(venda.getTotal());
                totalCell.setCellStyle(priceStyle);

                // Cabeçalho dos itens da venda
                Row headerItemRow = sheet.createRow(rowId++);
                Cell subtitle0 = headerItemRow.createCell(0);
                subtitle0.setCellValue("Produto");
                subtitle0.setCellStyle(bold);

                Cell subtitle1 = headerItemRow.createCell(1);
                subtitle1.setCellValue("Quantidade");
                subtitle1.setCellStyle(bold);

                Cell subtitle2 = headerItemRow.createCell(2);
                subtitle2.setCellValue("Data Do item");
                subtitle2.setCellStyle(bold);

                Cell subtitle3 = headerItemRow.createCell(3);
                subtitle3.setCellValue("Subtotal");
                subtitle3.setCellStyle(bold);

                // Preenchendo os itens da venda
                for (Item_Venda item : venda.getVenda()) {
                    Row itemVenda = sheet.createRow(rowId++);
                    Cell produtoCell = itemVenda.createCell(0);
                    produtoCell.setCellValue(item.getProduto().getNome());
                    produtoCell.setCellStyle(style);

                    Cell qtdCell = itemVenda.createCell(1);
                    qtdCell.setCellValue(item.getQtde());
                    qtdCell.setCellStyle(style);

                    Cell dataCellItem = itemVenda.createCell(2);
                    dataCellItem.setCellValue(item.getData());
                    dataCellItem.setCellStyle(dateStyle);

                    Cell subtotalCell = itemVenda.createCell(3);
                    subtotalCell.setCellValue(item.getSubtotal());
                    subtotalCell.setCellStyle(priceStyle);
                }

                // Divisória para separar cada venda
                Row divisoriaFinal = sheet.createRow(rowId++);
                divisoriaFinal.createCell(0).setCellStyle(bgStyle);
                divisoriaFinal.createCell(1).setCellStyle(bgStyle);
                divisoriaFinal.createCell(2).setCellStyle(bgStyle);
                divisoriaFinal.createCell(3).setCellStyle(bgStyle);
            }

            // Auto dimensionando as colunas
            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escrita no ByteArrayOutputStream e retorno
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

}
