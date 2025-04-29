package com.example.Intesis.Entity.Venda;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idvenda;

    private Date data;

    private  Float total;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda")
    @JsonManagedReference
    private List<Item_Venda> venda;
 }
