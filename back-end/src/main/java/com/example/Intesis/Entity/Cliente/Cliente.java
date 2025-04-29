package com.example.Intesis.Entity.Cliente;

import com.example.Intesis.Entity.Venda.Venda;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idcliente;

    private String nome;

    private String documento;

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Venda> vendas;
}
