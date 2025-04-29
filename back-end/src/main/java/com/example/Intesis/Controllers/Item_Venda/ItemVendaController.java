package com.example.Intesis.Controllers.Item_Venda;

import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Respositories.ItemVendaRepository;
import com.example.Intesis.Services.ItemVendaServices;
import com.example.Intesis.Services.VendaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/item")
public class ItemVendaController {

    @Autowired
    public ItemVendaRepository itemVendaRepository;

    @Autowired
    public ItemVendaServices itemVendaServices;

    @Autowired
    public VendaServices vendaServices;

    @PostMapping
    private String venda(@RequestBody Item_Venda venda){
        try{
            if(venda.getVenda() == null) return "Venda invalida";
            itemVendaRepository.save(venda);
            return "Venda Adicionada";

        } catch (Exception e) {
            return "Erro ao adicionar venda";
        }
    }

    @DeleteMapping("/{id}/{idVenda}")
    private ResponseEntity<String> venda(@PathVariable UUID id, @PathVariable UUID idVenda){
        try {
            itemVendaRepository.deleteById(id);
            itemVendaServices.atualizarVenda(idVenda);
            return ResponseEntity.ok("Item Deletado!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Item não foi deletado");
        }
    }

    @PutMapping("/{id}/{idVenda}")
    private ResponseEntity<String> vendaAtualizar(@PathVariable UUID id, @PathVariable UUID idVenda,
                                                  @RequestBody Integer qtde){
        try {
            itemVendaServices.atualizarItemVenda(id, qtde, idVenda);
            return ResponseEntity.ok("Item Atualizado!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Item não foi Atualizado " + e);
        }
    }

    @GetMapping
    private List<Item_Venda> venda(){
        return itemVendaRepository.findAll();
    }

}
