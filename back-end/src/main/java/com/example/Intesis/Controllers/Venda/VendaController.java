package com.example.Intesis.Controllers.Venda;

import com.example.Intesis.Dto.ItensVendaDTO;
import com.example.Intesis.Dto.VendaDTO;
import com.example.Intesis.Dto.VendaUpdateDto;
import com.example.Intesis.Entity.Venda.Venda;
import com.example.Intesis.Respositories.ClienteRepository;
import com.example.Intesis.Respositories.VendaRespository;
import com.example.Intesis.Services.VendaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendaRespository vendaRespository;

    @Autowired
    private VendaServices vendaServices;


    @GetMapping
    private List<Venda> venda() {
        return vendaRespository.findAll();
    }

    @GetMapping("/cliente/{id}")
    private List<Venda> vendaCliente(@PathVariable UUID id){
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException ("Nenhuma Venda Encontrada.")).getVendas();
    }

    @PutMapping
    public ResponseEntity<String> vendaUpdate(@RequestBody VendaUpdateDto venda){
        try{
            return vendaServices.atualizarVendaCompleta(venda);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Não Foi Possivel Atualizar A Venda" + e);
        }
    }

    @PostMapping
    private ResponseEntity<String> venda(@RequestBody VendaDTO body){
        ResponseEntity<String> resposta = vendaServices.criarVenda(body);
        return resposta;
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> venda(@PathVariable UUID id){
        try{
            vendaRespository.deleteById(id);
            return ResponseEntity.ok("Venda Excluida");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Não Foi Possivel Excluir o Venda");
        }
    }
}
