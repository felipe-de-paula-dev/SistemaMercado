package com.example.Intesis.Entity.Item_Venda;

import com.example.Intesis.Entity.Produto.Produto;
import com.example.Intesis.Entity.Venda.Venda;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "item_venda")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor



public class Item_Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID iditemvenda;

    private Date data;

    private float subtotal;

    private int qtde;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "idvenda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "idproduto")
    private Produto produto;
}
