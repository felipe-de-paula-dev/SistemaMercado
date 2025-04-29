package com.example.Intesis.Dto;

import com.example.Intesis.Entity.Item_Venda.Item_Venda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaUpdateDto {
    private UUID idvenda;

    private Date data;

    private Float total;

    private UUID idcliente;

    private List<Item_Venda> itensvenda;
}
