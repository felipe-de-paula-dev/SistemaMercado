package com.example.Intesis.Controllers.Excel;

import com.example.Intesis.Services.ExcelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/excel")
public class ClienteExcelController {

    @Autowired
    private ExcelServices excelServices;

    @GetMapping("/clientes/{id}")
    public ResponseEntity<byte[]> exportarClientes(@PathVariable UUID id) throws IOException{
        ByteArrayInputStream stream = excelServices.exportarClientes(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clientes.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream.readAllBytes());
    }

}
