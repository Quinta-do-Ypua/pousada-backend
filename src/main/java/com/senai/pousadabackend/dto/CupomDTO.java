package com.senai.pousadabackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CupomDTO {

    private Long id;

    private String codigo;

    private String nome;

    private LocalDate dataDeInicio;

    private LocalDate dataDeVencimento;

    private Double porcentagemDeDesconto;
}
