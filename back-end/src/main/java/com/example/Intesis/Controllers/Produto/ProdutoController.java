package com.example.Intesis.Controllers.Produto;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Respositories.ProdutoRepository;
import com.example.Intesis.Services.ProdutoServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoServices produtoServices;

    @GetMapping("/search")
    public List<Produto> produtoNome(@RequestParam String nome){
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping
    public List<Produto> produto(){
        return produtoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> produto(@RequestBody Produto produto){
        try {
            String mensagem = produtoServices.adicionarProduto(produto);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Produto Não Adicionado");
        }
    }

    @PutMapping
    public ResponseEntity<String> produtoUpdate(@RequestBody Produto body){
        try {
            String mensagem = produtoServices.atualizarProduto(body);
            return ResponseEntity.ok(mensagem);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Produto Não Foi Atualizado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar produto");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cliente(@PathVariable UUID id){
        try{
            produtoRepository.deleteById(id);
            return ResponseEntity.ok("Produto Excluido");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Não Foi Possivel Excluir o Produto");
        }
    }
}
