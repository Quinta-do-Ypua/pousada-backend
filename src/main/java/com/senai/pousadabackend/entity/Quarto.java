package com.senai.pousadabackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "quartos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false)
    private BigDecimal valorDiaria;

    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

}
