package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.example.Intesis.Entity.Venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemVendaRepository extends JpaRepository<Item_Venda, UUID> {
    void deleteByVenda(Venda venda);
}
