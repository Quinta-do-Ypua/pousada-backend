package com.senai.pousadabackend.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cupom")
@Table(name = "cupons")
public class Cupom {

    @Id
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data-inicio", nullable = false)
    private String dataDeInicio;

    @Column(name = "data-vencimento", nullable = false)
    private String dataDeVencimento;

    @Column(name = "porcentagem-desconto", nullable = false)
    private String porcentagemDeDesconto;
}
