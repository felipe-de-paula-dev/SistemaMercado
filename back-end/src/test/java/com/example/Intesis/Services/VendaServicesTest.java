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
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class VendaServicesTest {

    @Mock
    @Autowired
    private ClienteRepository clienteRepository;


    @Mock
    @Autowired
    private ItemVendaRepository itemVendaRepository;


    @Mock
    @Autowired
    private ProdutoRepository produtoRepository;


    @Mock
    @Autowired
    private VendaRespository vendaRespository;

    @InjectMocks
    @Autowired
    private VendaServices vendaServices;

    @Test
    void criarVenda_Sucesso() {
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();

        Cliente cliente = new Cliente(); cliente.setIdcliente(clienteId);
        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        ItensVendaDTO dto = new ItensVendaDTO();
        dto.setIdproduto(produto.getIdproduto());
        dto.setQtde(2);

        List<ItensVendaDTO> lista = new ArrayList<>();
        lista.add(dto);

        VendaDTO dtoVenda = new VendaDTO();
        dtoVenda.setIdcliente(clienteId);
        dtoVenda.setItensvenda(lista);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(vendaRespository.save(any())).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<String> resposta = vendaServices.criarVenda(dtoVenda);

        assertEquals(200, resposta.getStatusCodeValue());

        assertEquals("Venda Adicionada Com Sucesso", resposta.getBody());
    }

    @Test
    void criarVendaProdutoNaoEncontrado() {
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setIdcliente(clienteId);

        List<ItensVendaDTO> lista = new ArrayList<>();

        ItensVendaDTO item = new ItensVendaDTO();
        item.setIdproduto(produtoId);
        item.setQtde(2);

        lista.add(item);

        VendaDTO dtoVenda = new VendaDTO();
        dtoVenda.setItensvenda(lista);
        dtoVenda.setIdcliente(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        ResponseEntity<String> resposta = vendaServices.criarVenda(dtoVenda);

        assertEquals(404, resposta.getStatusCodeValue());
        assertTrue(resposta.getBody().contains("Não foi possivel adicionar a venda"));
    }

    @Test
    void criarVendaClienteNaoEncontrado() {
        UUID clienteId = UUID.randomUUID();
        VendaDTO dtoVenda = new VendaDTO();
        dtoVenda.setIdcliente(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        ResponseEntity<String> resposta = vendaServices.criarVenda(dtoVenda);

        assertEquals(404, resposta.getStatusCodeValue());
        assertTrue(resposta.getBody().contains("Não foi possivel adicionar a venda"));
    }



    @Test
    void atualizarVendaCompleta_Sucesso() {
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID vendaId = UUID.randomUUID();

        Produto produto = new Produto(); produto.setIdproduto(produtoId); produto.setPreco(50f);

        Venda newVenda = new Venda();

        Item_Venda dto = new Item_Venda();
        dto.setIditemvenda(vendaId);
        dto.setData(new Date());
        dto.setSubtotal(10);
        dto.setQtde(2);
        dto.setVenda(newVenda);
        dto.setProduto(produto);

        List<Item_Venda> lista = new ArrayList<>();
        lista.add(dto);


        VendaUpdateDto update = new VendaUpdateDto();
        update.setIdcliente(clienteId);
        update.setItensvenda(lista);
        update.setData(new Date());
        update.setIdvenda(vendaId);
        update.setTotal(100f);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(vendaRespository.save(any())).thenAnswer(i -> i.getArgument(0));


        ResponseEntity<String> resposta =  vendaServices.atualizarVendaCompleta(update);


        assertEquals(200, resposta.getStatusCodeValue());

        assertEquals("Venda Atualizada.", resposta.getBody());
    }
}