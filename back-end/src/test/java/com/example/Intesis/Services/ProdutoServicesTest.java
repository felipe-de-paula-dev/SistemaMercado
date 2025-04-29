package com.example.Intesis.Services;

import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Respositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServicesTest {

    @Mock
    private  ProdutoRepository produtoRepository;

    @InjectMocks
    private  ProdutoServices produtoService;

    @Test
    void adicionarProduto() {
        Produto produto = new Produto();
        produto.setPreco(100f);
        produto.setNome("Produto Teste");

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        String resposta = produtoService.adicionarProduto(produto);

        assertEquals("Produto Adicionado", resposta);
    }

    @Test
    void atualizarProduto() {
        UUID idproduto = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setIdproduto(idproduto);
        produto.setPreco(100f);
        produto.setNome("Produto Teste");


        Produto newproduto = new Produto();
        newproduto.setIdproduto(idproduto);
        newproduto.setPreco(150f);
        newproduto.setNome("Produto Teste2");

        when(produtoRepository.findById(idproduto)).thenReturn(Optional.of(produto));

        String resposta = produtoService.atualizarProduto(newproduto);

        assertEquals("Produto Atualizado Com Sucesso", resposta);
    }

    @Test
    void adicionarProdutoComPrecoInvalido() {
        Produto produto = new Produto();
        produto.setPreco(0f);
        produto.setNome("Produto Invalido");

        String resposta = produtoService.adicionarProduto(produto);

        assertEquals("Erro ao Adicionar Produto", resposta);
    }

    @Test
    void atualizarProdutoProdutoNaoEncontrado() {
        UUID idproduto = UUID.randomUUID();
        Produto newProduto = new Produto();
        newProduto.setIdproduto(idproduto);
        newProduto.setPreco(150f);
        newProduto.setNome("Produto Novo");

        when(produtoRepository.findById(idproduto)).thenReturn(Optional.empty());

        String resposta = produtoService.atualizarProduto(newProduto);

        assertEquals("Ocorreu Um Erro Ao Tentar Atualizar java.lang.RuntimeException: Produto Não Encontrado", resposta);
    }
}