package com.example.Intesis.Services;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Entity.Venda.Venda;
import com.example.Intesis.Respositories.ClienteRepository;
import com.example.Intesis.Respositories.ItemVendaRepository;
import com.example.Intesis.Respositories.ProdutoRepository;
import com.example.Intesis.Respositories.VendaRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ItemVendaServicesTest {

    @InjectMocks
    private ItemVendaServices itemVendaServices;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private VendaRespository vendaRespository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void atualizarVenda() {
        UUID vendaId = UUID.randomUUID();
        UUID iditem = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Cliente cliente = new Cliente(); cliente.setIdcliente(clienteId);
        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        Venda newVenda = new Venda();

        Item_Venda newItem = new Item_Venda();

        newItem.setProduto(produto);
        newItem.setSubtotal(20f);
        newItem.setIditemvenda(iditem);
        newItem.setQtde(5);
        newItem.setData(new Date());
        newItem.setVenda(newVenda);


        List<Item_Venda> lista = new ArrayList<>();

        lista.add(newItem);

        newVenda.setIdvenda(vendaId);
        newVenda.setCliente(cliente);
        newVenda.setTotal(100f);
        newVenda.setData(new Date());
        newVenda.setVenda(lista);

        when(vendaRespository.findById(vendaId)).thenReturn(Optional.of(newVenda));
        when(vendaRespository.save(any(Venda.class))).thenReturn(newVenda);

        ResponseEntity<String> response = itemVendaServices.atualizarVenda(vendaId);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void atualizarVendaNaoEncontrada() {
        UUID vendaId = UUID.randomUUID();
        UUID iditem = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Cliente cliente = new Cliente(); cliente.setIdcliente(clienteId);
        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        Venda newVenda = new Venda();

        Item_Venda newItem = new Item_Venda();

        newItem.setProduto(produto);
        newItem.setSubtotal(20f);
        newItem.setIditemvenda(iditem);
        newItem.setQtde(5);
        newItem.setData(new Date());
        newItem.setVenda(newVenda);


        List<Item_Venda> lista = new ArrayList<>();

        lista.add(newItem);

        newVenda.setIdvenda(vendaId);
        newVenda.setCliente(cliente);
        newVenda.setTotal(100f);
        newVenda.setData(new Date());
        newVenda.setVenda(lista);

        vendaRespository.save(newVenda);

        ResponseEntity<String> response = itemVendaServices.atualizarVenda(vendaId);
        assertEquals(500, response.getStatusCodeValue());
    }


    @Test
    void atualizarItemVenda() {
        UUID vendaId = UUID.randomUUID();
        UUID iditem = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Cliente cliente = new Cliente(); cliente.setIdcliente(clienteId);
        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        Venda newVenda = new Venda();

        Item_Venda newItem = new Item_Venda();

        newItem.setProduto(produto);
        newItem.setSubtotal(20f);
        newItem.setIditemvenda(iditem);
        newItem.setQtde(5);
        newItem.setData(new Date());
        newItem.setVenda(newVenda);


        List<Item_Venda> lista = new ArrayList<>();

        lista.add(newItem);

        newVenda.setIdvenda(vendaId);
        newVenda.setCliente(cliente);
        newVenda.setTotal(100f);
        newVenda.setData(new Date());
        newVenda.setVenda(lista);

        when(itemVendaRepository.findById(iditem)).thenReturn(Optional.of(newItem));

        String response = itemVendaServices.atualizarItemVenda(iditem, 6, vendaId);

        assertEquals("Item Atualizado Com Sucesso", response);
    }

    @Test
    void atualizarItemVendaSemSucesso() {
        UUID vendaId = UUID.randomUUID();
        UUID iditem = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();

        Cliente cliente = new Cliente(); cliente.setIdcliente(clienteId);
        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        Venda newVenda = new Venda();

        Item_Venda newItem = new Item_Venda();

        newItem.setProduto(produto);
        newItem.setSubtotal(20f);
        newItem.setIditemvenda(iditem);
        newItem.setQtde(5);
        newItem.setData(new Date());
        newItem.setVenda(newVenda);


        List<Item_Venda> lista = new ArrayList<>();

        lista.add(newItem);

        newVenda.setIdvenda(vendaId);
        newVenda.setCliente(cliente);
        newVenda.setTotal(100f);
        newVenda.setData(new Date());
        newVenda.setVenda(lista);

        when(itemVendaRepository.findById(iditem)).thenReturn(Optional.empty());

        String response = itemVendaServices.atualizarItemVenda(iditem, 6, vendaId);

        assertEquals("Erro ao atualizar item de venda", response);
    }
}