package com.example.Intesis.Services;

import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Respositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProdutoServices {

    @Autowired
    private ProdutoRepository produtoRepository;

    public String adicionarProduto(Produto produto) {
       try{
           if (produto.getPreco() <= 0) {
               throw new RuntimeException("Valor inválido");
           }
           produtoRepository.save(produto);
           return "Produto Adicionado";
       } catch (Exception e) {
           return "Erro ao Adicionar Produto";
       }
    }

    public String atualizarProduto(Produto body) {
        try{
            Produto produtoExistente = produtoRepository.findById(body.getIdproduto()).orElseThrow(() -> new RuntimeException("Produto Não Encontrado"));

            if(produtoExistente == null) throw  new RuntimeException("Produto Não Encontrado");

            produtoExistente.setNome(body.getNome());
            produtoExistente.setPreco(body.getPreco());

            produtoRepository.save(produtoExistente);
            return "Produto Atualizado Com Sucesso";
        } catch (Exception e) {
            return  "Ocorreu Um Erro Ao Tentar Atualizar " + e;
        }
    }
}