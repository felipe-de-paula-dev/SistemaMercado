package com.example.Intesis.Controllers.Excel;

import com.example.Intesis.Services.ExcelServices;
import com.example.Intesis.Services.VendaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/excel")
public class VendaExcelController {


    @Autowired
    private ExcelServices excelServices;

    @GetMapping("/vendas/{dataInicial}/{dataFinal}")
    public ResponseEntity<byte[]> exportarClientes(@PathVariable String dataInicial, @PathVariable String dataFinal){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date datastart = sdf.parse(dataInicial);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date dataend = sdf2.parse(dataFinal);
            // Gerando o relatório no formato ByteArrayInputStream
            ByteArrayInputStream relatório = excelServices.exportarVendasPorPeriodo(datastart, dataend);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=relatorio_vendas.xlsx");

            // Retornando o arquivo gerado como um byte array
            return new ResponseEntity<>(relatório.readAllBytes(), headers, HttpStatus.OK);

        } catch (IOException | ParseException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
