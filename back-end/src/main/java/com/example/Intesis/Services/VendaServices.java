package com.example.Intesis.Services;

import com.example.Intesis.Dto.ItensVendaDTO;
import com.example.Intesis.Dto.VendaDTO;
import com.example.Intesis.Dto.VendaUpdateDto;
import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Entity.Venda.Venda;
import com.example.Intesis.Respositories.ClienteRepository;
import com.example.Intesis.Respositories.ItemVendaRepository;
import com.example.Intesis.Respositories.ProdutoRepository;
import com.example.Intesis.Respositories.VendaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Struct;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VendaServices {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRespository vendaRespository;

    public ResponseEntity<String> criarVenda(VendaDTO dto){
       try{
           float totalVenda = 0;

           // Adicionar Cliente
           Cliente cliente = clienteRepository.findById(dto.getIdcliente()).orElseThrow();

           // Criar Venda
           Date dataAtual = new java.util.Date();
           Venda newVenda = new Venda();
           newVenda.setCliente(cliente);
           newVenda.setTotal((float) 0);
           newVenda.setData(new Date());

           Venda vendaSalva = vendaRespository.save(newVenda);

           //Para cada item da venda
           for(ItensVendaDTO itemDto : dto.getItensvenda()){
               Produto produto = produtoRepository.findById(itemDto.getIdproduto()).orElseThrow();

               float subTotal = produto.getPreco() * itemDto.getQtde();

               Item_Venda item = new Item_Venda();
               item.setProduto(produto);
               item.setQtde(itemDto.getQtde());
               item.setSubtotal(subTotal);
               item.setData(new Date());
               item.setVenda(vendaSalva);

               itemVendaRepository.save(item);
               totalVenda += subTotal;
           }

           //Atualizar total da venda
           vendaSalva.setTotal(totalVenda);
           vendaRespository.save(vendaSalva);

           return ResponseEntity.ok().body("Venda Adicionada Com Sucesso");
       } catch (Exception e) {
           return ResponseEntity.status(404).body("Não foi possivel adicionar a venda " + e);
       }
    }

    @Transactional
    public ResponseEntity<String> atualizarVendaCompleta(VendaUpdateDto venda){
        Cliente cliente = clienteRepository.findById(venda.getIdcliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (venda.getItensvenda() == null || venda.getItensvenda().isEmpty()) {
            throw new RuntimeException("A lista de itens da venda está vazia ou nula");
        }

        try{
            Venda editVenda = new Venda();
            editVenda.setIdvenda(venda.getIdvenda());
            editVenda.setCliente(cliente);
            editVenda.setData(venda.getData());
            editVenda.setTotal(venda.getTotal());
            editVenda.setVenda(venda.getItensvenda());

            Venda vendaEditada = vendaRespository.save(editVenda);

            itemVendaRepository.deleteByVenda(editVenda);

            for(Item_Venda item : venda.getItensvenda()){
                Produto produto = produtoRepository.findById(item.getProduto().getIdproduto()).get();

                float subTotal = produto.getPreco() * item.getQtde();

                Item_Venda newItem = new Item_Venda();
                newItem.setProduto(produto);
                newItem.setQtde(item.getQtde());
                newItem.setSubtotal(subTotal);
                newItem.setData(new Date());
                newItem.setVenda(vendaEditada);

                itemVendaRepository.save(newItem);
            }
            return ResponseEntity.ok("Venda Atualizada.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Venda Não Atualizada." + e);
        }
    }


}
