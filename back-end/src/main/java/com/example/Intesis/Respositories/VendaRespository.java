package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VendaRespository extends JpaRepository<Venda, UUID> {
    List<Venda> findAllByCliente(Cliente cliente);
    List<Venda> findAllByDataBetween(Date dataInicial, Date dataFinal);
}
