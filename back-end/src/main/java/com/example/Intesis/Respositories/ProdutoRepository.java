package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

}
