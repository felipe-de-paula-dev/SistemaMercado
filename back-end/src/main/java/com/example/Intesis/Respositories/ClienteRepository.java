package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
