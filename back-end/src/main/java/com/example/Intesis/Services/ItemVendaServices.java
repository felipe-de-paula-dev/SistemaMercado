package com.example.Intesis.Services;

import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Entity.Venda.Venda;
import com.example.Intesis.Respositories.ItemVendaRepository;
import com.example.Intesis.Respositories.VendaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemVendaServices {

    @Autowired
    private VendaRespository vendaRespository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;


    public ResponseEntity<String> atualizarVenda(UUID id){
        try {

            Venda newVenda = vendaRespository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venda não encontrada com o ID: " + id));

            float soma = 0f;
            for(Item_Venda i : newVenda.getVenda()){
                soma += i.getSubtotal();
            }

            Venda venda = new Venda();

            venda.setVenda(newVenda.getVenda());
            venda.setIdvenda(newVenda.getIdvenda());
            venda.setCliente(newVenda.getCliente());
            venda.setData(newVenda.getData());
            venda.setTotal(soma);

            vendaRespository.save(venda);
            return ResponseEntity.ok("Venda Atualizada Com Sucesso");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar Venda " + e);
        }
    }

    public String atualizarItemVenda(UUID id, Integer qtde, UUID idVenda) {
        try {
            Item_Venda venda = itemVendaRepository.findById(id).get();

            Produto produto = venda.getProduto();
            float subtotal = qtde * produto.getPreco();

            venda.setQtde(qtde);
            venda.setSubtotal(subtotal);

            itemVendaRepository.save(venda);

            this.atualizarVenda(idVenda);
            return "Item Atualizado Com Sucesso";
        } catch (Exception e) {
            return ("Erro ao atualizar item de venda");
        }
    }
}
